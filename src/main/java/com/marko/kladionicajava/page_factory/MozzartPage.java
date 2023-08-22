package com.marko.kladionicajava.page_factory;

import com.marko.kladionicajava.entitiy.League;
import com.marko.kladionicajava.entitiy.MatchDTO;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MozzartPage {
    private final int currentYear = LocalDate.now().getYear();

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @FindBy(xpath = "//button[contains(text(),'Sa')]")
    WebElement btnSacuvaj;
    @FindBy(xpath = "//button[contains(text(),'Allow')]")
    WebElement btnAllow;

    @FindBy(xpath = "//span[contains(text(),'Fudbal')]")
    WebElement btnFootbal;

    @FindBy(xpath = "//*[contains(@class, 'leagueheader')]")
    WebElement btnFootbal1;

    @FindBy(xpath = "//*[contains(@class, 'vb-dragger-styler')]")
    List<WebElement> listScrollElement;


    private WebDriver driver;

    public MozzartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void goAddress(String address) {
        try {
            this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
            this.driver.get(address);
        } catch (Exception e) {
            e.printStackTrace();
            this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        }
        this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));

    }

    public void setTimeReview(String hours) {

        driver.findElement(By.xpath("//li[contains(text(),'3 dana')]")).click();

//        String jsCode = "let element = document.querySelector('li:contains(\"tri dana\")');\n" +
//                "element.click();";
//        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
//        jsExecutor.executeScript(jsCode);
    }

    public void waitForPageToLoad() throws InterruptedException {

        WebElement scrollBar = driver.findElement(By.xpath("//*[contains(@class, 'bar-bar vb vb-visible')]")).findElement(By.xpath("div[1]"));


        // Kreiranje akcija
        Actions actions = new Actions(driver);
        btnFootbal1.click();
        for (int i = 0; i < 10; i++) {

            actions.moveToElement(scrollBar);
            actions.sendKeys(org.openqa.selenium.Keys.ARROW_DOWN);
            actions.perform();
        }
    }

    public List<MatchDTO> writeMatch() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        List<Object> matches = (List<Object>) js.executeScript(
                "let list = document.querySelectorAll('.match.botFlex');\n" +
                        "        let result = [];\n" +
                        "        list.forEach(e=>{\n" +
                        "        console.log(e.querySelector('.bonus-group'));\n" +
                        "          \n" +
                        "        if(e.querySelector('.sp-mark') === null){\n" +
                        "        result.push({\n" +
                        "           code: e.querySelector('.moreoddstext').innerText,\n" +
                        "           name: e.querySelector('.pairs').outerText.split('\\n')[0] + \" - \" + e.querySelector('.pairs').outerText.split('\\n')[1],\n" +
                        "           time: e.querySelector('.time').outerText.slice(e.querySelector('.time').outerText.indexOf(\".\") + 2),\n" +
                        "           date: '25.05.' \n" +
                        "             \n" +
                        "        }); }\n" +
                        "        });\n" +
                        "        return result;");

        List<MatchDTO> matches1 = new ArrayList<>();
        for (Object obj : matches) {
            if (obj instanceof Map) {
                Map<String, Object> matchMap = (Map<String, Object>) obj;
                String date = (String) matchMap.get("date");
                date = date + currentYear;
                String time = (String) matchMap.get("time");
                LocalDate datum = LocalDate.parse(date, formatter);
                String dateTimeString = datum.getYear() + "-" + datum.getMonthValue() + "-" + datum.getDayOfMonth() + " " + time;
                MatchDTO matchDTO = new MatchDTO();
                matchDTO.setCode((String) matchMap.get("code"));
                matchDTO.setName((String) matchMap.get("name"));
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

//                try {
//                   // matchDTO.setTime(dateFormat.parse(dateTimeString));
//                } catch (ParseException e) {
//                    throw new RuntimeException(e);
//                }
                matches1.add(matchDTO);
            }
        }
        return matches1;

    }

    public void clickLeague(List<League> leagues) {
        WebElement elementParent = driver.findElement(By.cssSelector(".main-item.active.all-active"));
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        for (int i = 0; i < leagues.size(); i++) {
            try {
                League league = leagues.get(i);
                if (league.getReview()) {
                    WebElement element = elementParent.findElement(By.xpath(".//span[contains(text(),'" + league.getNameLeague() + "')]"));
                    if (element != null) {
                        element.click();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

    }

    public List<MatchDTO> getAllMatches(String addressMozzart, String timeReviewMozzart, List<League> leagues) throws InterruptedException {
        goAddress(addressMozzart);
        btnSacuvaj.click();
        goAddress(addressMozzart);
        btnAllow.click();
        setTimeReview("12");
        btnFootbal.click();
        clickLeague(leagues);
        Thread.sleep(3000);
        // waitForPageToLoad();
        return writeMatch();
    }
}
