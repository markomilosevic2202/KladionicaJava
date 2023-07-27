package com.marko.kladionicajava.service;

import com.marko.kladionicajava.entitiy.*;
import com.marko.kladionicajava.page_factory.ForeignPage;
import com.marko.kladionicajava.repository.ClubNamesRepository;
import com.marko.kladionicajava.repository.MatchRepository;
import com.marko.kladionicajava.repository.QuotaRepository;
import com.marko.kladionicajava.tools.MaxBetService;
import com.marko.kladionicajava.tools.WebDriverMono;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final QuotaRepository quotaRepository;
    private final ClubNamesRepository clubNamesRepository;
    private final WebDriverMono webDriverMono;
    private final AppConfigService appConfigService;
    private final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private WebDriver driver;


    public void refreshShow() {
        driver = webDriverMono.open();
        MaxBetService maxBetService = new MaxBetService(driver);
        List<MatchDTO> listMatchPage = maxBetService.getAllMatchesBonus(appConfigService.getAddressMaxBet(), appConfigService.getTimeReview());
        driver.quit();
        //TODO list for Mozzart and Meridian
        List<Match> listMatchBase = matchRepository.findAll();

        for (int i = 0; i < listMatchPage.size(); i++) {
            Match match = new Match();
            MatchDTO matchDTOPage = listMatchPage.get(i);
            Optional<Match> optionalMatch;
            String matchName = matchDTOPage.getName();
            int delimiterIndex = matchName.indexOf(" - ");
            String clubNameHome = matchName.substring(0,delimiterIndex);

            if (clubNamesRepository.existByName(clubNameHome)) {
                ClubName clubName = new ClubName();
                clubName.setMaxbetName(clubNameHome);
                clubNamesRepository.save(clubName);
            }
            String clubNameForeign = matchName.substring(delimiterIndex + 3);

            if (clubNamesRepository.existByName(clubNameForeign)) {
                ClubName clubName = new ClubName();
                clubName.setMaxbetName(clubNameForeign);
                clubNamesRepository.save(clubName);
            }


            if (matchRepository.notExistsByIdMatch(matchDTOPage.getCode())) {
                match.setIdMatch(matchDTOPage.getCode());
                match.setNameHome(matchDTOPage.getName());
                match.setIdMatch(matchDTOPage.getCode());
                match.setDateMatch(matchDTOPage.getTime());
                match.setBettingShop(NameBetting.MAXBET);
                optionalMatch = Optional.of(matchRepository.save(match));
            }
        }
        quotaRepository.deleteAllMatchHaveStarted();
        matchRepository.deleteMatchStarted();
        findPairInForeignBettingShop();
       // matchRepository.deleteMatchByLinkForeignNull();
    }


    public void findPairInForeignBettingShop() {

        List<Match> list = matchRepository.findWithLinkForeignIsNull();
        driver = webDriverMono.open();
        ForeignPage foreignPage = new ForeignPage(driver);
        foreignPage.goAddress(appConfigService.getAddressForeign());
        for (int i = 0; i < list.size(); i++) {
            Match match = list.get(i);
            matchRepository.updateMatchLink(match, foreignPage.findLink(match.getNameHome()));
        }
        driver.quit();
    }


    public void refreshQuotas() {
        try {

            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM. HH:mm");
            String timeView = dateFormat.format(currentDate);
            driver = webDriverMono.open();
            MaxBetService maxBetService = new MaxBetService(driver);
            ForeignPage foreignPage = new ForeignPage(driver);
            List<Match> listMatchMaxbetBase = matchRepository.findAllByBettingShop(NameBetting.MAXBET);
            List<QuotaHomeDTO> listQuotasMaxbetPage = maxBetService.getAllQuotasBonus(appConfigService.getAddressMaxBet(), appConfigService.getTimeReview());
            Float bet = appConfigService.getBet();

            for (int i = 0; i < listMatchMaxbetBase.size(); i++) {
                Quotas quotas = new Quotas();
                Match match = listMatchMaxbetBase.get(i);
                QuotaForeignDTO quotaForeignDTO = foreignPage.getQuotaForeign(match.getLinkForeign());
                QuotaHomeDTO quotaHomeDTO = findMatchByCode(listQuotasMaxbetPage, match.getIdMatch());
                if(match != null && quotaHomeDTO != null && quotaForeignDTO != null ) {
                    quotaRepository.save(setQuotas(quotaForeignDTO, quotaHomeDTO, match, timeView, bet));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            driver.quit();
        }
        driver.quit();

    }


    public List<String> getOptionalView() {

        return quotaRepository.findAllDistinctByNumberOfView();
    }

    public Match getMatch(String matchId) {
        Optional<Match> match = matchRepository.findById(matchId);
        return match.get();
    }

    public Quotas setQuotas(QuotaForeignDTO quotaForeignDTO, QuotaHomeDTO quotaHomeDTO, Match match, String timeView, Float bet) {
        Quotas quotas = new Quotas();
        quotas.setMatches(match);
        quotas.setQuotaOne(Float.valueOf(decimalFormat.format(Float.parseFloat(quotaHomeDTO.getOne()))));
        quotas.setQuotaTwo(Float.valueOf(decimalFormat.format(Float.parseFloat(quotaHomeDTO.getTwo()))));
        quotas.setQuotaX(Float.valueOf(decimalFormat.format(Float.parseFloat(quotaHomeDTO.getX()))));
        quotas.setDifferenceOne(Float.valueOf(decimalFormat.format(quotaForeignDTO.getTwoXQuota())));
        quotas.setDifferenceTwo(Float.valueOf(decimalFormat.format(quotaForeignDTO.getOneXQuota())));
        quotas.setDifferenceX(Float.valueOf(decimalFormat.format(quotaForeignDTO.getOneTwoQuota())));
        quotas.setBetOne(Float.valueOf(decimalFormat.format(quotaForeignDTO.getTwoXBet())));
        quotas.setBetTwo(Float.valueOf(decimalFormat.format(quotaForeignDTO.getOneXBet())));
        quotas.setBetX(Float.valueOf(decimalFormat.format(quotaForeignDTO.getOneTwoQuota())));
        quotas.setProfitOne(Float.valueOf(decimalFormat.format(recalculateProfit(quotas.getQuotaOne(), quotas.getDifferenceOne(),bet))));
        quotas.setProfitTwo(Float.valueOf(decimalFormat.format(recalculateProfit(quotas.getQuotaTwo(), quotas.getDifferenceTwo(),bet))));
        quotas.setProfitX(Float.valueOf(decimalFormat.format(recalculateProfit(quotas.getQuotaX(), quotas.getDifferenceX(),bet))));
        quotas.setTimeView(timeView);
        return quotas;

    }

    public static Float recalculateProfit(Float homeQuota, Float foreignQuota,  Float bet){

        return (bet * homeQuota / foreignQuota) - (bet * homeQuota / (100/(homeQuota * 100 - 100) + 1));

    }

    public static QuotaHomeDTO findMatchByCode(List<QuotaHomeDTO> listMatchMaxbetBase, String searchCode) {
        for (QuotaHomeDTO quotaHomeDTO : listMatchMaxbetBase) {
            if (quotaHomeDTO.getCode().equals(searchCode)) {
                return quotaHomeDTO;
            }
        }
        return null;
    }
}
//konsider my self    start it