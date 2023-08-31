package com.marko.kladionicajava.service;


import com.marko.kladionicajava.entitiy.*;
import com.marko.kladionicajava.page_factory.ForeignPage;
import com.marko.kladionicajava.page_factory.MozzartPage;
import com.marko.kladionicajava.repository.LeagueRepository;
import com.marko.kladionicajava.repository.MatchRepository;
import com.marko.kladionicajava.repository.QuotaRepository;
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
    private final LeagueRepository leagueRepository;
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
        try {
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

        } catch (Exception e) {
            e.printStackTrace();

        }
        return quotas;
    }

    public static Float recalculateProfit(Float homeQuota, Float foreignQuota, Float bet) {

        return (bet * homeQuota / (100 / (homeQuota * 100 - 100) + 1)) - (bet * homeQuota / foreignQuota);

    }

    public void refreshQuotas() {
        try {
            quotaRepository.deleteAllQuotasWhereMatchHaveStarted();
            matchRepository.deleteMatchStarted();
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM. HH:mm");
            String timeView = dateFormat.format(currentDate);
            driver = webDriverMono.open();
            MozzartPage mozzartPage = new MozzartPage(driver);
            ForeignPage foreignPage = new ForeignPage(driver);
            Float minimumQuota = appConfigService.getMinimumQuota();
            Float minimumBet = appConfigService.getBet();
            Float minimumProfit = appConfigService.getMinimumProfit();
            List<Match> listMatchMozzartBase = matchRepository.findAllByBettingShop(NameBetting.MOZZART);
            List<QuotaHomeDTO> listQuotasMozzartPage = mozzartPage.getAllQuotas(appConfigService.getAddressMozzart(), appConfigService.getTimeReviewMozzart(), leagueRepository.findAll());
            if (listQuotasMozzartPage.size() != 0) {
                Float bet = appConfigService.getBet();
                for (int i = 0; i < listMatchMozzartBase.size(); i++) {
                    Match match = listMatchMozzartBase.get(i);
                    try {
                        QuotaHomeDTO quotaHomeDTO = findMatchByNameMatch(listQuotasMozzartPage, match.getNameHome());
                        if (match.getReview() && match.getLinkForeign() != null) {
                            QuotaForeignDTO quotaForeignDTO = foreignPage.getQuotaForeign(match.getLinkForeign());
                            if (match != null && quotaHomeDTO != null && quotaForeignDTO != null) {
                                Quotas quotas = setQuotas(quotaForeignDTO, quotaHomeDTO, match, timeView, bet);
                                quotaRepository.save(quotas);
                                filtersQuotas(quotas, minimumQuota, minimumBet, minimumProfit);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
            driver.quit();
        }


    }

    private void filtersQuotas(Quotas quotas, Float minimumQuota, Float minimumBet, Float minimumProfit) {
        if (quotas.getProfitOne() > minimumProfit) {
            if (quotas.getDifferenceOne() > minimumQuota) {
                if (quotas.getBetOne() > minimumBet) {
                    sentQuotas(quotas);
                }
            }

        } else if (quotas.getProfitTwo() > minimumProfit) {
            if (quotas.getDifferenceTwo() > minimumQuota) {
                if (quotas.getBetTwo() > minimumBet) {
                    sentQuotas(quotas);
                }
            }

        } else if (quotas.getProfitX() > minimumProfit) {
            if (quotas.getDifferenceX() > minimumQuota) {
                if (quotas.getBetX() > minimumBet) {
                    sentQuotas(quotas);
                }
            }
        }
    }

    private void sentQuotas(Quotas quotas) {
        System.out.println("**************************************************************************************");
    }


    public static QuotaHomeDTO findMatchByNameMatch(List<QuotaHomeDTO> listMatchMozzartBase, String searchNameMatch) {
        for (QuotaHomeDTO quotaHomeDTO : listMatchMozzartBase) {
            if (quotaHomeDTO.getName().equals(searchNameMatch)) {
                return quotaHomeDTO;
            }
        }
        return null;
    }
}
///likvi base    logere kamelkeis adresa kontrolera postgre baza 100java pitanja