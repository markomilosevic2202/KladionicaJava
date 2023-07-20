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


    private WebDriver webDriver;



    public List<MatchDTO> getAllMatchesMaxBetBonus(String address, String hoursOfReview) {
        List<MatchDTO> listMaxbetBonusMatch = new ArrayList<>();

        try {

             listMaxbetBonusMatch = getListMaxbetOrdinaryMatch(address, hoursOfReview);
             quit();
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
            webDriver = open();
            MaxBet maxBet = new MaxBet(webDriver);
            maxBet.goAddress(address);
            Thread.sleep(10000);
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
    public  WebDriver open() {
        try {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--remote-allow-origins=*");
            chromeOptions.addArguments("start-maximized"); // open Browser in maximized mode
            chromeOptions.addArguments("disable-infobars"); // disabling infobars
            chromeOptions.addArguments("--disable-extensions"); // disabling extensions
            chromeOptions.addArguments("--disable-gpu"); // applicable to windows os only
            chromeOptions.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
            chromeOptions.addArguments("--no-sandbox"); // Bypass OS security model
            this.webDriver = new ChromeDriver(chromeOptions);
            this.webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
            this.webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            this.webDriver.manage().window().maximize();
            return webDriver;
        } catch (Exception e) {
            e.printStackTrace();
            this.webDriver.close();
        }
        return null;
    }
    public  void quit(){
       try {
           this.webDriver.quit();
       }
       catch (Exception e){
           System.out.println("ssdfsdfsd");
       }

    }


}
