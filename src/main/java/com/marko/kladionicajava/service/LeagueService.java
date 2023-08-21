package com.marko.kladionicajava.service;


import com.marko.kladionicajava.entitiy.Email;
import com.marko.kladionicajava.entitiy.League;
import com.marko.kladionicajava.repository.LeagueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LeagueService {

    private final LeagueRepository leagueRepository;

    public List<League> getAllLeague() {
        try {
            return leagueRepository.findAll();

        } catch (Exception e) {

            e.printStackTrace();
            return new ArrayList<>();
        }


    }

    public void createLeague(League league) {

        try {
            Optional<League> optionalLeague = leagueRepository.existByLeagueName(league.getNameLeague());
            if (optionalLeague.isEmpty()) {
                league.setReview(true);
                leagueRepository.save(league);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteLeague(String leagueId){
        try {
            Optional<League> optionalLeague = leagueRepository.findById(leagueId);
            if (optionalLeague.isPresent()){
                leagueRepository.deleteById(leagueId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateLeague() {

    }
}
