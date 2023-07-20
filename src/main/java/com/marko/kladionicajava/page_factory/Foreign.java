package com.marko.kladionicajava.page_factory;


import com.marko.kladionicajava.entitiy.Match;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



public class Foreign {


    @FindBy(className = "biab_search-input")
    WebElement inpSearch;

    @FindBy(xpath = "//a[contains(text(),'Double Chance')]")
    WebElement btnDoubleChance;


    private Actions actions;
    WebDriver driver;

    public Foreign(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    public void inputSearch(String nameClub) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
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




}