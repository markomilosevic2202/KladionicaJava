package com.marko.kladionicajava.service;

import com.marko.kladionicajava.entitiy.*;
import com.marko.kladionicajava.page_factory.ForeignPage;
import com.marko.kladionicajava.repository.MatchRepository;
import com.marko.kladionicajava.repository.QuotaRepository;
import com.marko.kladionicajava.tools.MaxBetService;
import com.marko.kladionicajava.tools.WebDriverMono;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final QuotaRepository quotaRepository;
    private final WebDriverMono webDriverMono;
    private final AppConfigService appConfigService;
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
        matchRepository.deleteMatchByLinkForeignNull();
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

            for (int i = 0; i < listMatchMaxbetBase.size(); i++) {
                Quotas quotas = new Quotas();
                Match match = listMatchMaxbetBase.get(i);
                QuotaForeignDTO quotaForeignDTO = foreignPage.getQuotaForeign(match.getLinkForeign());
                QuotaHomeDTO quotaHomeDTO = findMatchByCode(listQuotasMaxbetPage, match.getIdMatch());
                if(match != null && quotaHomeDTO != null && quotaForeignDTO != null ) {
                    quotaRepository.save(setQuotas(quotaForeignDTO, quotaHomeDTO, match, timeView));
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

    public Quotas setQuotas(QuotaForeignDTO quotaForeignDTO, QuotaHomeDTO quotaHomeDTO, Match match, String timeView) {
        Quotas quotas = new Quotas();
        quotas.setMatches(match);
        quotas.setQuotaOne(Float.parseFloat(quotaHomeDTO.getOne()));
        quotas.setQuotaTwo(Float.parseFloat(quotaHomeDTO.getTwo()));
        quotas.setQuotaX(Float.parseFloat(quotaHomeDTO.getX()));
        quotas.setDifferenceOne(quotaForeignDTO.getTwoXQuota());
        quotas.setDifferenceTwo(quotaForeignDTO.getOneXQuota());
        quotas.setDifferenceX(quotaForeignDTO.getOneTwoQuota());
        quotas.setBetOne(quotaForeignDTO.getTwoXBet());
        quotas.setBetTwo(quotaForeignDTO.getOneXBet());
        quotas.setBetX(quotaForeignDTO.getOneTwoQuota());
        quotas.setTimeView(timeView);
        return quotas;

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