package com.marko.kladionicajava.tools;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@RequiredArgsConstructor
@Configuration
public class WebDriverMono {
    private WebDriver driver;

    public WebDriver open() {
        try {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--remote-allow-origins=*");
            chromeOptions.addArguments("start-maximized"); // open Browser in maximized mode
            chromeOptions.addArguments("disable-infobars"); // disabling infobars
            chromeOptions.addArguments("--disable-extensions"); // disabling extensions
            chromeOptions.addArguments("--disable-gpu"); // applicable to windows os only
            chromeOptions.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
            chromeOptions.addArguments("--no-sandbox");
            //chromeOptions.addArguments("--headless");// Bypass OS security model
            this.driver = new ChromeDriver(chromeOptions);
            this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
            this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            this.driver.manage().window().maximize();
            return driver;
        } catch (Exception e) {
            e.printStackTrace();
            this.driver.quit();
        }
        return null;
    }
    public  void quit(){
        try {
            this.driver.quit();
        }
        catch (Exception e){
            System.out.println("ssdfsdfsd");
        }

    }
}
