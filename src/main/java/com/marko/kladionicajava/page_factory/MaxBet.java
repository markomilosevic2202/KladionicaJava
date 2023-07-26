package com.marko.kladionicajava.page_factory;


import com.marko.kladionicajava.entitiy.Match;
import com.marko.kladionicajava.entitiy.MatchDTO;
import com.marko.kladionicajava.entitiy.QuotaHomeDTO;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MaxBet {

    private final int currentYear = LocalDate.now().getYear();

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final DateTimeFormatter baseFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");


    @FindBy(xpath = "//div[contains(text(),'Fudbal')]")
    private List<WebElement> listFootball;

    @FindBy(xpath = "/html/body/div[1]/div[5]/div/div[2]/div[1]/div[2]/div[4]/div/div[1]/div[2]/div[1]")
    private WebElement btnSelectAll;

    @FindBy(xpath = "//*[contains(text(),'Max Bonus Tip Fudbal ')]")
    private WebElement btnMaxBonus;

    @FindBy(xpath = "//*[contains(@class, 'home-game bck-col-1 ng-scope')]")
    private List<WebElement> listMatchDiv;

    @FindBy(xpath = "//*[contains(@class, 'ui-slider-handle ui-state-default ui-corner-all')]")
    private WebElement slider;

    @FindBy(xpath = "//*[contains(@class,'ngdialog-close ng-scope')]")
    private WebElement btnClosePopup;

    private final WebDriver driver;

    public MaxBet(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public void clickFootball() {
        this.listFootball.get(0).click();

    }

    public void clickSelectAll() {
        this.btnSelectAll.click();
    }

    public void clickMaxBonus() {
        this.btnMaxBonus.click();

    }
    public void clickClosePopup() {
        this.btnClosePopup.click();

    }

    public void clickSlider(String hours) {

        this.slider.click();
        if (hours.equals("3")) {
            for (int i = 0; i < 5; i++) {
                this.slider.sendKeys(Keys.ARROW_LEFT);
            }

        } else if (hours.equals("5")) {
            for (int i = 0; i < 4; i++) {
                this.slider.sendKeys(Keys.ARROW_LEFT);
            }
        } else if (hours.equals("12")) {
            for (int i = 0; i < 3; i++) {
                this.slider.sendKeys(Keys.ARROW_LEFT);
            }
        } else if (hours.equals("24")) {
            for (int i = 0; i < 2; i++) {
                this.slider.sendKeys(Keys.ARROW_LEFT);
            }
        } else if (hours.equals("48")) {
            this.slider.sendKeys(Keys.ARROW_LEFT);
        } else if (hours.equals("all")) {

        } else {
            // Assertions.fail("  :: The specified parameter was not found. The options offered are 3,5,12,24,48,all :: ");
        }


    }

    public List<MatchDTO> getBonusMatch() {

        JavascriptExecutor js = (JavascriptExecutor) this.driver;
        List<Object> resultList = (List<Object>) js.executeScript(
                "let list = document.querySelectorAll('.home-game.bck-col-1.ng-scope');\n" +
                        "let result = [];\n" +
                        "list.forEach(e=>{\n" +
                        "    result.push({\n" +
                        "        \"code\": e.querySelector('.cc-match-code').innerText,\n" +
                        "        \"name\": e.querySelector('.teams-overflow').textContent.replaceAll('\\n', ''),\n" +
                        "        \"time\": e.querySelector('.cc-match-kickoff').outerText.split(' ')[1],\n" +
                        "        \"date\": e.querySelector('.cc-match-kickoff').outerText.split(' ')[0]\n" +
                        "\n" +
                        "    });\n" +
                        "});\n" +
                        "return result;");

        List<MatchDTO> matches = new ArrayList<>();
        for (Object obj : resultList) {
            if (obj instanceof Map) {
                Map<String, Object> matchMap = (Map<String, Object>) obj;
                String date =(String) matchMap.get("date");
                date = date  + currentYear ;
                String time =(String) matchMap.get("time");
                LocalDate datum = LocalDate.parse(date, formatter);
                String dateTimeString = datum.getYear() + "-" + datum.getMonthValue() + "-" + datum.getDayOfMonth() + " " + time;
                MatchDTO matchDTO = new MatchDTO();
                matchDTO.setCode((String) matchMap.get("code"));
                matchDTO.setName((String) matchMap.get("name"));
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                try {
                    matchDTO.setTime(dateFormat.parse(dateTimeString));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                matches.add(matchDTO);
            }
        }
        return matches;


    }

    public List<QuotaHomeDTO> getQuota() {

        JavascriptExecutor js = (JavascriptExecutor) this.driver;
        List<Object> resultList = (List<Object>) js.executeScript(
                "let list = document.querySelectorAll('.home-game.bck-col-1.ng-scope');\n" +
                        "let result = [];\n" +
                        "list.forEach(e=>{\n" +
                        "    result.push({\n" +
                        "        \"code\": e.querySelector('.cc-match-code').innerText,\n" +
                        "        \"one\": e.getElementsByTagName('odd')[0].outerText,\n" +
                        "        \"x\": e.getElementsByTagName('odd')[1].outerText,\n" +
                        "        \"two\": e.getElementsByTagName('odd')[2].outerText,\n" +
                        "\n" +
                        "    });\n" +
                        "});\n" +
                        "return result;");

        List<QuotaHomeDTO> quota = new ArrayList<>();
        for (Object obj : resultList) {
            if (obj instanceof Map) {
                Map<String, Object> matchMap = (Map<String, Object>) obj;

                QuotaHomeDTO quotaHomeDTO = new QuotaHomeDTO();
                quotaHomeDTO.setCode((String) matchMap.get("code"));
                quotaHomeDTO.setOne((String) matchMap.get("one"));
                quotaHomeDTO.setTwo((String) matchMap.get("two"));
                quotaHomeDTO.setX((String) matchMap.get("x"));
                quota.add(quotaHomeDTO);
            }
        }
        return quota;


    }

    public void goAddress(String address) {
        this.driver.get(address);
    }
}
