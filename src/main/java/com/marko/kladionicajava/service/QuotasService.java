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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuotasService {

    private final QuotaRepository quotaRepository;
    private final MatchRepository matchRepository;
    private final WebDriverMono webDriverMono;
    private final AppConfigService appConfigService;
    private final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private WebDriver driver;

    public List<Quotas> getAllQuotasLastView(String timeView) {

        try {
            return quotaRepository.findAllByNumber(timeView);

        } catch (Exception e) {

            e.printStackTrace();
            return new ArrayList<>();
        }
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
        quotas.setProfitOne(Float.valueOf(decimalFormat.format(recalculateProfit(quotas.getQuotaOne(), quotas.getDifferenceOne(), bet))));
        quotas.setProfitTwo(Float.valueOf(decimalFormat.format(recalculateProfit(quotas.getQuotaTwo(), quotas.getDifferenceTwo(), bet))));
        quotas.setProfitX(Float.valueOf(decimalFormat.format(recalculateProfit(quotas.getQuotaX(), quotas.getDifferenceX(), bet))));
        quotas.setTimeView(timeView);
        return quotas;

    }

    public static Float recalculateProfit(Float homeQuota, Float foreignQuota, Float bet) {

        return (bet * homeQuota / (100 / (homeQuota * 100 - 100) + 1)) - (bet * homeQuota / foreignQuota) ;

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
            List<QuotaHomeDTO> listQuotasMaxbetPage = maxBetService.getAllQuotasBonus(appConfigService.getAddressMaxBet(), appConfigService.getTimeReviewMaxbet());
            Float bet = appConfigService.getBet();

            for (int i = 0; i < listMatchMaxbetBase.size(); i++) {
                Match match = listMatchMaxbetBase.get(i);
                if (match.getReview()) {
                    QuotaForeignDTO quotaForeignDTO = foreignPage.getQuotaForeign(match.getLinkForeign());
                    QuotaHomeDTO quotaHomeDTO = findMatchByCode(listQuotasMaxbetPage, match.getIdMatch());
                    if (match != null && quotaHomeDTO != null && quotaForeignDTO != null) {
                        quotaRepository.save(setQuotas(quotaForeignDTO, quotaHomeDTO, match, timeView, bet));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            driver.quit();
        }
        driver.quit();

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
///likvi base    logere kamelkeis adresa kontrolera postgre baza 100java pitanja