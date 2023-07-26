package com.marko.kladionicajava.page_factory;


import com.marko.kladionicajava.entitiy.QuotaForeignDTO;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


public class ForeignPage {


    @FindBy(className = "biab_search-input")
    WebElement inpSearch;

    @FindBy(xpath = "//a[contains(text(),'Double Chance')]")
    WebElement btnDoubleChance;




    private Actions actions;
    WebDriver driver;

    public ForeignPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }
    public void goAddress(String address) {
        this.driver.get(address);
    }

    public void inputSearch(String nameClub) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        inpSearch.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        inpSearch.sendKeys(nameClub);
    }

    public void clickFirstMatchOnFindList() {
        WebElement firstMatchOnList = driver.findElement(By.xpath("//*[contains(@class, 'biab_search-results js-search-results')]"));
        firstMatchOnList.findElement(By.xpath(".//a")).click();
    }

    public void clickButtonDoubleChance(){
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Correct Score')]")));
        btnDoubleChance.click();
    }
    public String getAddress(){
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        return driver.getCurrentUrl();

    }

    public String findLink(String nameClub) {
        try {
            inputSearch(nameClub);
            clickFirstMatchOnFindList();
            clickButtonDoubleChance();
            return getAddress();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public QuotaForeignDTO getQuotaForeign(String linkForeign) throws InterruptedException {
        QuotaForeignDTO quotaForeignDTO = new QuotaForeignDTO();
        goAddress(linkForeign);
        Thread.sleep(3000);
        List<WebElement> listQuota = driver.findElements(By.xpath("//*[contains(@class, 'biab_bet biab_blue-cell js-blue-cell biab_bet-back js-bet-back biab_back-0 js-back-0')]"));
      //  new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Double Chance')]")));
       quotaForeignDTO.setOneXQuota(Float.parseFloat(listQuota.get(0).findElement(By.xpath("//*[contains(@class,'js-odds')]")).getText()));
        quotaForeignDTO.setOneXBet(Float.parseFloat(listQuota.get(0).findElement(By.xpath("//*[contains(@class,'biab_bet-amount')]")).getText()));
        quotaForeignDTO.setTwoXQuota(Float.parseFloat(listQuota.get(1).findElement(By.xpath("//*[contains(@class,'js-odds')]")).getText()));
        quotaForeignDTO.setTwoXBet(Float.parseFloat(listQuota.get(1).findElement(By.xpath("//*[contains(@class,'biab_bet-amount')]")).getText()));
        quotaForeignDTO.setOneTwoQuota(Float.parseFloat(listQuota.get(2).findElement(By.xpath("//*[contains(@class,'js-odds')]")).getText()));
        quotaForeignDTO.setOneTwoBet(Float.parseFloat(listQuota.get(2).findElement(By.xpath("//*[contains(@class,'biab_bet-amount')]")).getText()));
        return quotaForeignDTO;



    }
}