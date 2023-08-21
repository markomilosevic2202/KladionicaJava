package com.marko.kladionicajava.repository;

import com.marko.kladionicajava.entitiy.ClubName;
import com.marko.kladionicajava.entitiy.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeagueRepository extends JpaRepository<League, String> {

    @Query("select l from League l where l.nameLeague = ?1")
    Optional<League> existByLeagueName(String nameLeague);
}
