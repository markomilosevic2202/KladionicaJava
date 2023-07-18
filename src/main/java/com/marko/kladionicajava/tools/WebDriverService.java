package com.marko.kladionicajava.tools;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class WebDriverService {


    private WebDriver webDriver;
    private static final WebDriverService instance = new WebDriverService();

    private WebDriverService() {
        try {
            if(instance != null){
                return;
            }
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
            webDriver.manage().window().maximize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WebDriverService getInstance() {
        return instance;
    }

    public static WebDriver getWebdriver() {
        return getInstance().webDriver;
    }

}
