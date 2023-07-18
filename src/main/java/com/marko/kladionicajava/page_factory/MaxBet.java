package com.marko.kladionicajava.page_factory;


import com.marko.kladionicajava.entitiy.Match;
import com.marko.kladionicajava.entitiy.MatchDTO;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

//    public void waitForPageToLoad() throws InterruptedException {
//        JavascriptExecutor js = (JavascriptExecutor) this.driver;
//        WebElement scrollBar = (WebElement) js.executeScript("return document.querySelector('body > div > div:nth-child(3) > div > div > div.slimScrollBar');");
//        boolean isScrollBarAtEnd = false;
//        // skrolovanje na kraj skrol bara
//        while (!isScrollBarAtEnd) {
//
//
//            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
//            Thread.sleep(500);
//            isScrollBarAtEnd = (boolean) js.executeScript("return (document.documentElement.scrollHeight - window.innerHeight) === window.scrollY");
//
//
//        }
//    }

    public List<Match> writeMatch() {
        JavascriptExecutor js = (JavascriptExecutor) this.driver;
        List<Match> matches = (List<Match>) js.executeScript(
                "let list = document.querySelectorAll('.home-game.bck-col-1.ng-scope');\n" +
                        "let result = [];\n" +
                        "list.forEach(e=>{\n" +
                        "    result.push({\n" +
                        "        \"code\": e.querySelector('.cc-match-code').innerText,\n" +
                        "        \"name\": e.querySelector('.teams-overflow').textContent.replaceAll('\\n', ''),\n" +
                        "        \"one\": e.getElementsByTagName('odd')[0].outerText,\n" +
                        "        \"x\": e.getElementsByTagName('odd')[1].outerText,\n" +
                        "        \"two\": e.getElementsByTagName('odd')[2].outerText,\n" +
                        "        \"time\": e.querySelector('.cc-match-kickoff').outerText.split(' ')[1],\n" +
                        "        \"date\": e.querySelector('.cc-match-kickoff').outerText.split(' ')[0]\n" +
                        "\n" +
                        "    });\n" +
                        "});\n" +
                        "return result;");
        System.out.println(matches.size());
        return matches;

//        List<Match> listObject = new ArrayList<>();
//        System.out.println(listMatchDiv.size());
//        for (int i = 0; i < listMatchDiv.size(); i++) {
//            WebElement element = listMatchDiv.get(i);
//            Match match = new Match();
//            match.setCode(element.findElement(By.xpath("match/span/div/div[1]")).getText());
//            String fullDate = element.findElement(By.xpath("match/span/div/div[2]")).getText();
////            match.setTime(fullDate.substring(7,12));
//            match.setTime(fullDate.split(" ")[1]);
////            match.setDate(fullDate.substring(0,5));
//            match.setDate(fullDate.split(" ")[0]);
//            match.setName(element.findElement(By.xpath("match/span/div/div[3]")).getText());
//            match.setOne(element.findElement(By.xpath("match/span/div/div[5]/span[1]/odd")).getText());
//            match.setTwo(element.findElement(By.xpath("match/span/div/div[5]/span[1]/odd[2]")).getText());
//            match.setX(element.findElement(By.xpath("match/span/div/div[5]/span[1]/odd[3]")).getText());
//            listObject.add(match);
    }

    public List<MatchDTO> writeBonusMatch() {

        JavascriptExecutor js = (JavascriptExecutor) this.driver;
        List<Object> resultList = (List<Object>) js.executeScript(
                "let list = document.querySelectorAll('.home-game.bck-col-1.ng-scope');\n" +
                        "let result = [];\n" +
                        "list.forEach(e=>{\n" +
                        "    result.push({\n" +
                        "        \"code\": e.querySelector('.cc-match-code').innerText,\n" +
                        "        \"name\": e.querySelector('.teams-overflow').textContent.replaceAll('\\n', ''),\n" +
                        "        \"one\": e.getElementsByTagName('odd')[0].outerText,\n" +
                        "        \"x\": e.getElementsByTagName('odd')[1].outerText,\n" +
                        "        \"two\": e.getElementsByTagName('odd')[2].outerText,\n" +
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
                // matchDTO.setOne((String) matchMap.get("one"));
                matchDTO.setTime(dateTimeString);
                matches.add(matchDTO);
            }
        }
        return matches;


    }

    public void goAddress(String address) {
        this.driver.get(address);
    }
}
