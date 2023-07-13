package com.marko.kladionicajava.repository;


import com.marko.kladionicajava.entitiy.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MatchRepository extends JpaRepository<Match, String> {
}
