package com.marko.kladionicajava.tools;

import com.marko.kladionicajava.entitiy.Match;
import com.marko.kladionicajava.entitiy.MatchDTO;
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


    public List<MatchDTO> getAllMatchesMaxBetBonus(String address, String hoursOfReview) {
        List<MatchDTO> listMaxbetBonusMatch = new ArrayList<>();

        try {

             listMaxbetBonusMatch = getListMaxbetOrdinaryMatch(address, hoursOfReview);
//
        } catch (Exception e) {
            e.printStackTrace();
        }
       // driver.quit();

        return listMaxbetBonusMatch;


    }

    private List<MatchDTO> getListMaxbetOrdinaryMatch(String address, String hoursOfReview) {
        try {
//

            this.driver = WebDriverService.getWebdriver();
            MaxBet maxBet = new MaxBet(driver);
            maxBet.goAddress(address);
            maxBet.clickSlider(hoursOfReview);
            Thread.sleep(1000);
            maxBet.clickFootball();
            Thread.sleep(2000);
            maxBet.clickMaxBonus();
            Thread.sleep(2000);
          //  maxBet.waitForPageToLoad();
           return maxBet.writeBonusMatch();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();

        }

    }


}
