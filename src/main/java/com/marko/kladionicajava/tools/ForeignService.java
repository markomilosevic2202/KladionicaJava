package com.marko.kladionicajava.tools;

import com.marko.kladionicajava.entitiy.MatchDTO;
import com.marko.kladionicajava.page_factory.Foreign;
import com.marko.kladionicajava.page_factory.MaxBet;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ForeignService {

    private WebDriver driver;

    public ForeignService(WebDriver driver) {
        this.driver = driver;
    }


    public String findLink(String nameClub) {
        try {
            Foreign foreign = new Foreign(driver);
            // this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
            foreign.goAddress("https://www.orbitxch.com/customer/inplay/highlights/1");
            foreign.inputSearch(nameClub);
            foreign.clickFirstMatchOnFindList();
            foreign.clickButtonDoubleChance();
            //  this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            return foreign.getAddress();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String getQuota(String linkMatch) {
        Foreign foreign = new Foreign(driver);
        foreign.goAddress(linkMatch);
    }


}
