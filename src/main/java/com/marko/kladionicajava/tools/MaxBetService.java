package com.marko.kladionicajava.tools;

import com.marko.kladionicajava.entitiy.MatchDTO;
import com.marko.kladionicajava.entitiy.QuotaHomeDTO;
import com.marko.kladionicajava.page_factory.MaxBet;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaxBetService {


    private WebDriver driver;

    public MaxBetService(WebDriver driver) {
        this.driver = driver;
    }

    public List<MatchDTO> getAllMatchesBonus(String address, String hoursOfReview) {
        List<MatchDTO> listMaxbetBonusMatch = new ArrayList<>();

        try {
            MaxBet maxBet = new MaxBet(driver);
            setPageBonusMatch(address, hoursOfReview, maxBet);
            listMaxbetBonusMatch = maxBet.getBonusMatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMaxbetBonusMatch;
    }


    public List<QuotaHomeDTO> getAllQuotasBonus(String address, String hoursOfReview) {
        List<QuotaHomeDTO> listMaxbetBonusQuotas = new ArrayList<>();

        try {
            MaxBet maxBet = new MaxBet(driver);
            setPageBonusMatch(address, hoursOfReview, maxBet);
            listMaxbetBonusQuotas = maxBet.getQuota();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMaxbetBonusQuotas;
    }

    private void setPageBonusMatch(String address, String hoursOfReview, MaxBet maxBet) {
        try {

            maxBet.goAddress(address);


            maxBet.clickSlider(hoursOfReview);

            maxBet.clickFootball();
            Thread.sleep(3500);


        } catch (Exception e) {
            e.printStackTrace();


        }
    }



}
