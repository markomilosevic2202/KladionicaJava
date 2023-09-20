package com.marko.kladionicajava.page_factory;

import com.marko.kladionicajava.entitiy.League;
import com.marko.kladionicajava.entitiy.MatchDTO;
import com.marko.kladionicajava.entitiy.QuotaHomeDTO;
import com.marko.kladionicajava.tools.DayService;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MozzartPage {

    private WebDriver driver;

    private DayService dayService;

    public MozzartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[contains(text(),'Sa')]")
    WebElement btnSacuvaj;
    @FindBy(xpath = "//button[contains(text(),'Cancel')]")
    WebElement btnCancel;

    @FindBy(xpath = "//*[contains(@class, 'clear-button')]")
    WebElement btnX;

    @FindBy(xpath = "//span[contains(text(),'Fudbal')]")
    WebElement btnFootbal;

    @FindBy(xpath = "//*[contains(@class, 'leagueheader')]")
    WebElement btnFootball1;


    public void goAddress(String address) {
        try {
            this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
            this.driver.get(address);
        } catch (Exception e) {
            System.out.println("************************************* Exception Address *********************************************");
            // this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        }
        this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
    }

    public void setTimeReview(String hours) {

        if (hours.equals("1")) {
            driver.findElement(By.xpath("//li[contains(text(),'Danas')]")).click();
        } else {
            driver.findElement(By.xpath("//li[contains(text(),'3 dana')]")).click();
        }
    }

    public void waitForPageToLoad() throws InterruptedException {

        WebElement scrollBar = driver.findElement(By.xpath("//*[contains(@class, 'bar-bar vb vb-visible')]")).findElement(By.xpath("div[1]"));
        Actions actions = new Actions(driver);
        btnFootball1.click();
        for (int i = 0; i < 200; i++) {
            actions.moveToElement(scrollBar);
            actions.sendKeys(org.openqa.selenium.Keys.ARROW_DOWN);
            actions.perform();
        }
        Thread.sleep(5000);
    }

    public List<MatchDTO> writeMatch() {
        try {
            List<Object> matchesPage = getMatchesPage();
            List<MatchDTO> matchesDTO = new ArrayList<>();
            dayService = new DayService();
            for (Object obj : matchesPage) {
                if (obj instanceof Map) {
                    Map<String, Object> matchMap = (Map<String, Object>) obj;
                    String date = dayService.getNextDayInWeek((String) matchMap.get("date"));
                    String timeString = (String) matchMap.get("time");
                    MatchDTO matchDTO = new MatchDTO();
                    matchDTO.setCode((String) matchMap.get("code"));
                    matchDTO.setName((String) matchMap.get("name"));
                    matchDTO.setLeague((String) matchMap.get("league"));
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    try {
                        matchDTO.setTime(dateFormat.parse(date + " " + timeString));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    matchesDTO.add(matchDTO);
                }
            }
            return matchesDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }


    }

    public List<QuotaHomeDTO> writeQuotas() {
        try {
            List<Object> quotasPage = getQuotasPage();
            List<QuotaHomeDTO> quotas = new ArrayList<>();
            if (quotasPage.size() > 0) {
                for (Object obj : quotasPage) {
                    if (obj instanceof Map) {
                        Map<String, Object> matchMap = (Map<String, Object>) obj;
                        QuotaHomeDTO quotaHomeDTO = new QuotaHomeDTO();
                        quotaHomeDTO.setName((String) matchMap.get("name"));
                        quotaHomeDTO.setOne((String) matchMap.get("one"));
                        quotaHomeDTO.setTwo((String) matchMap.get("two"));
                        quotaHomeDTO.setX((String) matchMap.get("x"));
                        quotas.add(quotaHomeDTO);
                    }
                }
            }
            return quotas;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

    public void clickLeague(List<League> leagues) {
        WebElement elementParent = driver.findElement(By.cssSelector(".main-item.active.all-active"));
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0, 500));
        for (int i = 0; i < leagues.size(); i++) {
            League league = leagues.get(i);
            try {
                String a = league.getNameLeague().toUpperCase();
                if (league.getReview()) {
                    WebElement element = elementParent.findElement(By.xpath(".//span[contains(text(),'" + a + "')]"));
                    if (element != null) {
                        element.click();
                    }
                }
            } catch (Exception e) {
                System.out.println("************************** Not found league: " + league.getNameLeague() + " **********************************");
            }
        }
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    public void setPage(String addressMozzart, String timeReviewMozzart, List<League> leagues) {
        try {
            goAddress(addressMozzart);
            this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
           // btnX.click();
            btnSacuvaj.click();
            btnCancel.click();
            this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            setTimeReview(timeReviewMozzart);
            btnFootbal.click();
            clickLeague(leagues);
            Thread.sleep(3000);
            waitForPageToLoad();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setPageSingleLeague(String addressMozzart, String timeReviewMozzart, List<League> leagues) {
        try {
            goAddress(addressMozzart);
            this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//            btnX.click();
            btnSacuvaj.click();
            btnCancel.click();
            this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            setTimeReview(timeReviewMozzart);
            btnFootbal.click();
            clickLeague(leagues);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<MatchDTO> getAllMatches(String addressMozzart, String timeReviewMozzart, List<League> leagues) {
        setPage(addressMozzart, timeReviewMozzart, leagues);
        return writeMatch();
    }

    public List<QuotaHomeDTO> getAllQuotas(String addressMozzart, String timeReviewMozzart, List<League> leagues) {
        setPage(addressMozzart, timeReviewMozzart, leagues);
        return writeQuotas();
    }


    public List<Object> getQuotasPage() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return (List<Object>) js.executeScript(
                    "let list = document.querySelectorAll('.match.botFlex');\n" +
                            "let result = [];\n" +
                            "list.forEach(e => {\n" +
                            "    console.log(e.querySelector('.bonus-group'));\n" +
                            "    if (e.querySelector('.sp-mark') === null && !e.querySelector('.bonus-group')) {\n" + // Dodata provera za .bonus-group
                            "        result.push({\n" +
                            "            one: e.querySelectorAll('.partvar.odds')[0].outerText.substring(e.querySelectorAll('.partvar.odds')[0].outerText.indexOf(\"\\n\") + 1),\n" +
                            "            x: e.querySelectorAll('.partvar.odds')[1].outerText.substring(e.querySelectorAll('.partvar.odds')[1].outerText.indexOf(\"\\n\") + 1),\n" +
                            "            two: e.querySelectorAll('.partvar.odds')[2].outerText.substring(e.querySelectorAll('.partvar.odds')[2].outerText.indexOf(\"\\n\") + 1),\n" +
                            "            name: e.querySelector('.pairs').outerText.split('\\n')[0] + \" - \" + e.querySelector('.pairs').outerText.split('\\n')[1],\n" +
                            "        });\n" +
                            "    }\n" +
                            "});\n" +
                            "return result;"
            );
        } catch (Exception e) {
            return new ArrayList<>();
        }

    }

    public List<Object> getMatchesPage() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return (List<Object>) js.executeScript(
                    "let list = document.querySelectorAll('.match.botFlex');\n" +
                            "let result = [];\n" +
                            "list.forEach(e => {\n" +
                            "    console.log(e.querySelector('.bonus-group'));\n" +
                            "    if (e.querySelector('.sp-mark') === null) {\n" +
                            "        result.push({\n" +
                            "            code: e.querySelector('.moreoddstext').innerText,\n" +
                            "            name: e.querySelector('.pairs').outerText.split('\\n')[0] + \" - \" + e.querySelector('.pairs').outerText.split('\\n')[1],\n" +
                            "            time: e.querySelector('.time').outerText.slice(e.querySelector('.time').outerText.indexOf(\".\") + 2),\n" +
                            "            date: e.querySelector('.time').outerText.slice(0, 4),\n" +
                            "          league: e.querySelector('.league').innerText,\n" +
                            "        });\n" +
                            "    }\n" +
                            "});\n" +
                            "return result;"
            );
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}