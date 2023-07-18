package com.marko.kladionicajava.service;

import com.marko.kladionicajava.entitiy.Match;
import com.marko.kladionicajava.entitiy.MatchDTO;
import com.marko.kladionicajava.entitiy.NameBetting;
import com.marko.kladionicajava.repository.MatchRepository;
import com.marko.kladionicajava.tools.MaxBetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

      private final MatchRepository matchRepository;
      private final MaxBetService maxBetService;

    public List<Match> getMatches() {

        try {
            return matchRepository.findAll();

        } catch (Exception e) {

            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void refreshMatches() {
        List<MatchDTO> listMatch = maxBetService.getAllMatchesMaxBetBonus("https://www.maxbet.rs/ibet-web-client/#/home#top", "5");
        List<Match> list = new ArrayList<>();
        for (int i = 0; i < listMatch.size(); i++) {
            Match match = new Match();
            MatchDTO matchDTO = listMatch.get(i) ;
            match.setIdMatch(matchDTO.getOne());
            match.setLinkOrbit(matchDTO.getName());
            match.setIdMatch(matchDTO.getCode());
            match.setDateMatch(matchDTO.getTime());
            match.setBettingShop(NameBetting.MAXBET);
            //   match.setOdds_one(Float.parseFloat(matchDTO.getOne()));

                list.add(match);
            }

        matchRepository.saveAll(list);
    }
}
//konsider my self    start it