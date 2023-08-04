package com.marko.kladionicajava.service;

import com.marko.kladionicajava.entitiy.*;
import com.marko.kladionicajava.page_factory.ForeignPage;
import com.marko.kladionicajava.repository.ClubNamesRepository;
import com.marko.kladionicajava.repository.MatchRepository;
import com.marko.kladionicajava.repository.QuotaRepository;
import com.marko.kladionicajava.tools.MaxBetService;
import com.marko.kladionicajava.tools.WebDriverMono;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final QuotaRepository quotaRepository;
    private final ClubNamesRepository clubNamesRepository;
    private final WebDriverMono webDriverMono;
    private final AppConfigService appConfigService;

    private WebDriver driver;


    public void refreshShow() {
        driver = webDriverMono.open();
        MaxBetService maxBetService = new MaxBetService(driver);
        List<MatchDTO> listMatchPage = maxBetService.getAllMatchesBonus(appConfigService.getAddressMaxBet(), appConfigService.getTimeReview());
        driver.quit();
        //TODO list for Mozzart and Meridian
        List<Match> listMatchBase = matchRepository.findAll();

        for (int i = 0; i < listMatchPage.size(); i++) {
            Match match = new Match();
            MatchDTO matchDTOPage = listMatchPage.get(i);
            Optional<Match> optionalMatch;
            String matchName = matchDTOPage.getName();
            int delimiterIndex = matchName.indexOf(" - ");
            String hostClubNameString = matchName.substring(0, delimiterIndex);

            if (clubNamesRepository.existByName(hostClubNameString)) {
                ClubName clubName = new ClubName();
                clubName.setMaxbetName(hostClubNameString);
                clubName.setMatchName(matchName);
                clubNamesRepository.save(clubName);
            }
            String guestClubNameString = matchName.substring(delimiterIndex + 3);

            if (clubNamesRepository.existByName(guestClubNameString)) {
                ClubName clubName = new ClubName();
                clubName.setMaxbetName(guestClubNameString);
                clubName.setMatchName(matchName);
                clubNamesRepository.save(clubName);
            }


            if (matchRepository.notExistsByIdMatch(matchDTOPage.getCode())) {

                ClubName hostClubName = clubNamesRepository.findByMaxbetName(hostClubNameString);
                ClubName guestClubName = clubNamesRepository.findByMaxbetName(guestClubNameString);
                match.setIdMatch(matchDTOPage.getCode());
                match.setNameHome(matchDTOPage.getName());
                match.setIdMatch(matchDTOPage.getCode());
                match.setDateMatch(matchDTOPage.getTime());
                match.setHostNameClub(hostClubName);
                match.setGuestNameClub(guestClubName);
                match.setBettingShop(NameBetting.MAXBET);
                matchRepository.save(match);
            }
        }
        quotaRepository.deleteAllMatchHaveStarted();
        matchRepository.deleteMatchStarted();
        findPairInForeignBettingShop();
        // matchRepository.deleteMatchByLinkForeignNull();
    }


    public void findPairInForeignBettingShop() {
        try {
            List<Match> list = matchRepository.findWithLinkForeignIsNull();
            driver = webDriverMono.open();
            ForeignPage foreignPage = new ForeignPage(driver);
            //foreignPage.goAddress(appConfigService.getAddressForeign());
            for (int i = 0; i < list.size(); i++) {
                try {
                    Match match = list.get(i);
                    matchRepository.updateMatchLink(match, foreignPage.findLink(match.getHostNameClub().getForeignName(),
                            match.getGuestNameClub().getForeignName(),
                            appConfigService.getAddressForeign()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
            driver.quit();
        }
    }

    public List<Match> findAllWithoutLink(String nameBetShop) {
        List<Match> listMatch;
        try {
             return matchRepository.findWithLinkForeignIsNull();

        } catch (Exception e) {
            e.printStackTrace();
           return listMatch = new ArrayList<>();
        }
    }





    public List<String> getOptionalView() {

        return quotaRepository.findAllDistinctByNumberOfView();
    }

    public Match getMatch(String matchId) {
        Optional<Match> match = matchRepository.findMatchById1(matchId);
        return match.get();
    }


}
//konsider my self    start it