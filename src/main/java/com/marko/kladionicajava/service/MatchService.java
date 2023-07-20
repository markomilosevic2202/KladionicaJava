package com.marko.kladionicajava.service;

import com.marko.kladionicajava.entitiy.Match;
import com.marko.kladionicajava.entitiy.MatchDTO;
import com.marko.kladionicajava.entitiy.NameBetting;
import com.marko.kladionicajava.entitiy.Adds;
import com.marko.kladionicajava.repository.MatchRepository;
import com.marko.kladionicajava.repository.AddsRepository;
import com.marko.kladionicajava.tools.ForeignService;
import com.marko.kladionicajava.tools.MaxBetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final AddsRepository addsRepository;
    private final MaxBetService maxBetService;
    private final ForeignService foreignService;
    private static int numberOfView = 0;



    public List<Match> getMatches() {

        try {
            return matchRepository.findAll();

        } catch (Exception e) {

            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void refreshMatches() {
        List<MatchDTO> listMatchPage = maxBetService.getAllMatchesMaxBetBonus("https://www.maxbet.rs/ibet-web-client/#/home#top", "12");
        //TODO list for Mozzart and Meridian
        List<Match> listMatchBase = matchRepository.findAll();
        numberOfView ++;


        for (int i = 0; i < listMatchPage.size(); i++) {
            Match match = new Match();
            MatchDTO matchDTOPage = listMatchPage.get(i);

            if (matchRepository.notExistsByIdMatch(matchDTOPage.getCode())) {
                match.setIdMatch(matchDTOPage.getCode());
                match.setNameHome(matchDTOPage.getName());
                match.setIdMatch(matchDTOPage.getCode());
                match.setDateMatch(matchDTOPage.getTime());
                match.setBettingShop(NameBetting.MAXBET);
                match.setOdds_one(Float.parseFloat(matchDTOPage.getOdds_one()));
                match.setOdds_two(Float.parseFloat(matchDTOPage.getOdds_two()));
                match.setOdds_x(Float.parseFloat(matchDTOPage.getOdds_x()));
                matchRepository.save(match);
            } else {
                Optional<Match> optionalMatch = matchRepository.findMatchByIdMatch(matchDTOPage.getCode());
                optionalMatch.get().setOdds_one(Float.parseFloat(matchDTOPage.getOdds_one()));
                optionalMatch.get().setOdds_two(Float.parseFloat(matchDTOPage.getOdds_two()));
                optionalMatch.get().setOdds_x(Float.parseFloat(matchDTOPage.getOdds_x()));//));
                matchRepository.save(optionalMatch.get());

            }
            //TODO write in quota table
            Adds adds = new Adds();
            adds.setDifference_one(Float.parseFloat(matchDTOPage.getOdds_one()));
            adds.setDifference_two(Float.parseFloat(matchDTOPage.getOdds_two()));
            adds.setDifference_x(Float.parseFloat(matchDTOPage.getOdds_x()));
            adds.setNumberOfView(numberOfView);
            addsRepository.save(adds);
        }
//        matchRepository.deleteMatchStarted();
//        findPairInForeignBettingShop();

    }






    public void findPairInForeignBettingShop() {

        List<Match> list = matchRepository.findWithLinkForeignIsNull();
        for (int i = 0; i < list.size(); i++) {
            Match match = list.get(i);
            matchRepository.updateMatchLink(match, foreignService.findLink(match.getNameHome()));


        }
    }

    public void refreshQuots() {
    }


}
//konsider my self    start it