package com.marko.kladionicajava.page_factory;


import com.marko.kladionicajava.entitiy.QuotaForeignDTO;
import org.openqa.selenium.By;
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

    @FindBy(xpath = "//*[contains(@class, 'styles_searchItem__link__t5gOP biab_search-item-wrapper')]")
    List<WebElement> listLinkFindMatch;


    private Actions actions;
    WebDriver driver;

    public ForeignPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    public void goAddress(String address) {
        try {
            this.driver.get(address);
        }catch (Exception e){
           // e.printStackTrace();
        }
    }



    public void clickFirstMatchOnFindList(String url) throws InterruptedException {
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'styles_searchItem__link__t5gOP biab_search-item-wrapper')]")));
        if (listLinkFindMatch.size() == 0) {
            goAddress(url);
            Thread.sleep(1500);
        }
        listLinkFindMatch.get(0).click();

    }

    public void clickButtonDoubleChance() {

           new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Double Chance')]")));
           //btnDoubleChance.click();
           driver.findElement(By.xpath("//li//*[text()='Double Chance']/ancestor::li")).findElement(By.xpath("a")).click();
//       }catch (Exception e){
//          // e.printStackTrace();
//           System.out.println("********************************************* Not Found Double Chance *****************************************");
//       }

    }

    public String getAddress() {
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        return driver.getCurrentUrl();

    }

    public String findLink(String homeClubName, String foreignClubName, String url) {
        try {
            if(homeClubName != null && foreignClubName!= null ){
            this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
            String url2 = " " + homeClubName + " v " + foreignClubName;
            url2 = url2.replace(" ", "+");
            goAddress(url + url2);
            clickFirstMatchOnFindList(url + url2);
            clickButtonDoubleChance();
            Thread.sleep(1000);
            }

        } catch (Exception e) {

            return null;
        }
        return getAddress();
    }


    public QuotaForeignDTO getQuotaForeign(String linkForeign) throws InterruptedException {
        try {
            this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
//            new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'betContentCellMarket biab_bet styles_betContentCell__\" +\n" +
//                    "                            \"-gv8u biab_bet-content-cell biab_blue-cell styles_cellBlock__cell__9h0XI biab_back-0 biab_bet-back back-cell')]")));
            QuotaForeignDTO quotaForeignDTO = new QuotaForeignDTO();
            goAddress(linkForeign);
            Thread.sleep(3000);
            WebElement elementOne = driver.findElements(By.xpath("//*[contains(@class, 'betContentCellMarket biab_bet styles_betContentCell__" +
                            "-gv8u biab_bet-content-cell biab_blue-cell styles_cellBlock__cell__9h0XI biab_back-0 biab_bet-back back-cell')]"))
                    .get(0);
            WebElement elementTwo = driver.findElements(By.xpath("//*[contains(@class, 'betContentCellMarket biab_bet styles_betContentCell__" +
                            "-gv8u biab_bet-content-cell biab_blue-cell styles_cellBlock__cell__9h0XI biab_back-0 biab_bet-back back-cell')]"))
                    .get(1);
            WebElement elementX = driver.findElements(By.xpath("//*[contains(@class, 'betContentCellMarket biab_bet styles_betContentCell__" +
                            "-gv8u biab_bet-content-cell biab_blue-cell styles_cellBlock__cell__9h0XI biab_back-0 biab_bet-back back-cell')]"))
                    .get(2);
            quotaForeignDTO.setOneXQuota(Float.parseFloat(elementOne.findElement(By.xpath("span/div/span[1]")).getText()));
            quotaForeignDTO.setOneXBet(Float.parseFloat(elementOne.findElement(By.xpath("span/div/span[2]")).getText()));
            quotaForeignDTO.setTwoXQuota(Float.parseFloat(elementTwo.findElement(By.xpath("span/div/span[1]")).getText()));
            quotaForeignDTO.setTwoXBet(Float.parseFloat(elementTwo.findElement(By.xpath("span/div/span[2]")).getText()));
            quotaForeignDTO.setOneTwoQuota(Float.parseFloat(elementX.findElement(By.xpath("span/div/span[1]")).getText()));
            quotaForeignDTO.setOneTwoBet(Float.parseFloat(elementX.findElement(By.xpath("span/div/span[2]")).getText()));
            return quotaForeignDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
}