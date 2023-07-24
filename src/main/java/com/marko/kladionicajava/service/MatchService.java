package com.marko.kladionicajava.service;

import com.marko.kladionicajava.entitiy.Match;
import com.marko.kladionicajava.entitiy.MatchDTO;
import com.marko.kladionicajava.entitiy.NameBetting;
import com.marko.kladionicajava.entitiy.Quotas;
import com.marko.kladionicajava.repository.MatchRepository;
import com.marko.kladionicajava.repository.QuotaRepository;
import com.marko.kladionicajava.tools.ForeignService;
import com.marko.kladionicajava.tools.MaxBetService;
import com.marko.kladionicajava.tools.WebDriverMono;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final QuotaRepository quotaRepository;
    private final WebDriverMono webDriverMono;
    private WebDriver driver;





    public List<Match> getMatches() {

        try {
            return matchRepository.findAll();

        } catch (Exception e) {

            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void refreshShow() {
        driver = webDriverMono.open();
        MaxBetService maxBetService = new MaxBetService(driver);
        List<MatchDTO> listMatchPage = maxBetService.getAllMatchesMaxBetBonus("https://www.maxbet.rs/ibet-web-client/#/home#top", "3");
        driver.quit();
        //TODO list for Mozzart and Meridian
        List<Match> listMatchBase = matchRepository.findAll();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM. HH:mm");
        String timeView = dateFormat.format(currentDate);


        for (int i = 0; i < listMatchPage.size(); i++) {
            Match match = new Match();
            MatchDTO matchDTOPage = listMatchPage.get(i);
            Optional<Match> optionalMatch;

            if (matchRepository.notExistsByIdMatch(matchDTOPage.getCode())) {
                match.setIdMatch(matchDTOPage.getCode());
                match.setNameHome(matchDTOPage.getName());
                match.setIdMatch(matchDTOPage.getCode());
                match.setDateMatch(matchDTOPage.getTime());
                match.setBettingShop(NameBetting.MAXBET);
                match.setOdds_one(Float.parseFloat(matchDTOPage.getOdds_one()));
                match.setOdds_two(Float.parseFloat(matchDTOPage.getOdds_two()));
                match.setOdds_x(Float.parseFloat(matchDTOPage.getOdds_x()));
                optionalMatch = Optional.of( matchRepository.save(match));

               ;
            } else {
                optionalMatch = matchRepository.findMatchByIdMatch(matchDTOPage.getCode());
                optionalMatch.get().setOdds_one(Float.parseFloat(matchDTOPage.getOdds_one()));
                optionalMatch.get().setOdds_two(Float.parseFloat(matchDTOPage.getOdds_two()));
                optionalMatch.get().setOdds_x(Float.parseFloat(matchDTOPage.getOdds_x()));//));
                optionalMatch = Optional.of(matchRepository.save(optionalMatch.get()));

            }
            //TODO write in quota table
            Quotas quota = new Quotas();
            quota.setDifference_one(Float.parseFloat(matchDTOPage.getOdds_one()));
            quota.setDifference_two(Float.parseFloat(matchDTOPage.getOdds_two()));
            quota.setDifference_x(Float.parseFloat(matchDTOPage.getOdds_x()));
            quota.setNumber(timeView);
            quota.setMatches(optionalMatch.get());
            quotaRepository.save(quota);

        }
        quotaRepository.deleteAllMatchHaveStarted();
        matchRepository.deleteMatchStarted();
        findPairInForeignBettingShop();

    }






    public void findPairInForeignBettingShop() {

        List<Match> list = matchRepository.findWithLinkForeignIsNull();
        driver = webDriverMono.open();
        ForeignService foreignService = new ForeignService(driver);
        for (int i = 0; i < list.size(); i++) {
            Match match = list.get(i);
            matchRepository.updateMatchLink(match, foreignService.findLink(match.getNameHome()));


        }
        driver.quit();
    }

    public void refreshQuots() {
    }


    public List<String> getOptionalView() {

        return  quotaRepository.findAllDistinctByNumberOfView();
    }
}
//konsider my self    start it