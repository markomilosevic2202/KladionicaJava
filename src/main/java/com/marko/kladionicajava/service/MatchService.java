package com.marko.kladionicajava.service;

import com.marko.kladionicajava.entitiy.Match;
import com.marko.kladionicajava.entitiy.MatchDTO;
import com.marko.kladionicajava.entitiy.NameBetting;
import com.marko.kladionicajava.repository.MatchRepository;
import com.marko.kladionicajava.tools.ForeignService;
import com.marko.kladionicajava.tools.MaxBetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final MaxBetService maxBetService;
    private final ForeignService foreignService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    public List<Match> getMatches() {

        try {
            return matchRepository.findAll();

        } catch (Exception e) {

            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void refreshMatches() {
        List<MatchDTO> listMatchPage = maxBetService.getAllMatchesMaxBetBonus("https://www.maxbet.rs/ibet-web-client/#/home#top", "5");
        List<Match> listMatchBase = matchRepository.findAll();


        for (int i = 0; i < listMatchBase.size(); i++) {
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
                optionalMatch.get().setOdds_x(Float.parseFloat(matchDTOPage.getOdds_two()));

            }
        }
    }

    LocalDateTime timeNow = LocalDateTime.now();


    LocalDateTime timeBase = LocalDateTime.parse(matchDTO.getTime(), formatter);

            if(timeBase.isBefore(timeNow)

    {

    }

        matchRepository.saveAll(list);
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