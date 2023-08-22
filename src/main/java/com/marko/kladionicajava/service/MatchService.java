package com.marko.kladionicajava.service;

import com.marko.kladionicajava.entitiy.*;
import com.marko.kladionicajava.page_factory.ForeignPage;
import com.marko.kladionicajava.page_factory.MozzartPage;
import com.marko.kladionicajava.repository.ClubNamesRepository;
import com.marko.kladionicajava.repository.LeagueRepository;
import com.marko.kladionicajava.repository.MatchRepository;
import com.marko.kladionicajava.repository.QuotaRepository;
import com.marko.kladionicajava.tools.MaxBetService;
import com.marko.kladionicajava.tools.WebDriverMono;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final QuotaRepository quotaRepository;
    private final ClubNamesRepository clubNamesRepository;
    private final WebDriverMono webDriverMono;
    private final AppConfigService appConfigService;
    private final LeagueRepository leagueRepository;

    private WebDriver driver;


    public void refreshShow() {
        List<MatchDTO> listMatchPage = new ArrayList<>();
//        try {
//        driver = webDriverMono.open();
//        MaxBetService maxBetService = new MaxBetService(driver);
//        listMatchPage = maxBetService.getAllMatchesBonus(appConfigService.getAddressMaxBet(), appConfigService.getTimeReviewMaxbet());
//        driver.quit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //TODO list for Mozzart and Meridian
        try {
            driver = webDriverMono.open();
            MozzartPage mozzartPage = new MozzartPage(driver);
            listMatchPage = mozzartPage.getAllMatches(appConfigService.getAddressMozzart(), appConfigService.getTimeReviewMozzart(),leagueRepository.findAll());
            driver.quit();
            List<Match> listMatchBase = matchRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < listMatchPage.size(); i++) {
            Match match = new Match();
            MatchDTO matchDTOPage = listMatchPage.get(i);
            Optional<Match> optionalMatch;
            String matchName = matchDTOPage.getName().trim();
            int delimiterIndex = matchName.indexOf(" - ");
            String hostClubNameString = matchName.substring(0, delimiterIndex);

            if (clubNamesRepository.existByName(hostClubNameString)) {
                ClubName clubName = new ClubName();
                clubName.setMozzartName(hostClubNameString);
                clubName.setMatchName(matchName);
                clubNamesRepository.save(clubName);
            }
            String guestClubNameString = matchName.substring(delimiterIndex + 3);

            if (clubNamesRepository.existByName(guestClubNameString)) {
                ClubName clubName = new ClubName();
                clubName.setMozzartName(guestClubNameString);
                clubName.setMatchName(matchName);
                clubNamesRepository.save(clubName);
            }


            if (matchRepository.notExistsByNameHome(matchDTOPage.getName())) {

                ClubName hostClubName = clubNamesRepository.findByMozzartName(hostClubNameString);
                ClubName guestClubName = clubNamesRepository.findByMozzartName(guestClubNameString);
                match.setIdMatch(matchDTOPage.getCode());
                match.setNameHome(matchDTOPage.getName());
                match.setIdMatch(matchDTOPage.getCode());
                match.setDateMatch(matchDTOPage.getTime());
                match.setHostNameClub(hostClubName);
                match.setGuestNameClub(guestClubName);
                match.setBettingShop(NameBetting.MOZZART);
                match.setReview(true);
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

    public List<Match> findAllWithoutLink() {
        List<Match> listMatch;
        try {
            return matchRepository.findWithLinkForeignIsNull();

        } catch (Exception e) {
            e.printStackTrace();
            return listMatch = new ArrayList<>();
        }
    }

    public List<Match> findAllHasLink() {
        List<Match> listMatch;
        try {
            return matchRepository.findWithLinkForeignIsNotNull();

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


    public void updateLink(Match matchPage) {

        try {

            Optional<Match> optionalMatch = matchRepository.findById(matchPage.getId());
            if (optionalMatch.isPresent()) {
                Match matchBase = optionalMatch.get();
                if (matchPage.getLinkForeign().length() > 15) {
                    matchBase.setLinkForeign(matchPage.getLinkForeign().trim());
                }
                matchRepository.save(matchBase);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateReview(Match matchPage) {

        try {
            Optional<Match> optionalMatch = matchRepository.findById(matchPage.getId());

            if (optionalMatch.isPresent()) {
                if (matchPage.getReview() == null) {
                    optionalMatch.get().setReview(false);
                } else {
                    optionalMatch.get().setReview(true);
                }
            }
            matchRepository.save(optionalMatch.get());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Match> findAllMatches() {
        return matchRepository.findAll();
    }
}
