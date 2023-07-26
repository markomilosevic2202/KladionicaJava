package com.marko.kladionicajava.tools;

import com.marko.kladionicajava.entitiy.Match;
import com.marko.kladionicajava.entitiy.MatchDTO;
import com.marko.kladionicajava.page_factory.MaxBet;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaxBetService {


    private WebDriver driver;

    public MaxBetService(WebDriver driver) {
        this.driver = driver;
    }

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

           // this.webDriver = WebDriverService.getWebdriver();

            MaxBet maxBet = new MaxBet(driver);
            maxBet.goAddress(address);
            Thread.sleep(6000);
            maxBet.clickSlider(hoursOfReview);
            Thread.sleep(500);
            maxBet.clickFootball();
            Thread.sleep(500);
            maxBet.clickMaxBonus();
          //  maxBet.clickSelectAll();
            Thread.sleep(1000);
          //  maxBet.waitForPageToLoad();
           return maxBet.writeBonusMatch();

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();

        }


    }



}
