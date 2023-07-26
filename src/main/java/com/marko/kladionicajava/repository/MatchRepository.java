package com.marko.kladionicajava.repository;


import com.marko.kladionicajava.entitiy.Email;
import com.marko.kladionicajava.entitiy.Match;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface MatchRepository extends JpaRepository<Match, String> {

    @Query("select m from Match m where m.linkForeign is null")
    List<Match> findWithLinkForeignIsNull();


    @Modifying
    @Transactional
    @Query("UPDATE Match m SET m.linkForeign = :newLink WHERE m = :match")
    void updateMatchLink(@Param("match") Match match, @Param("newLink") String newLink);

    @Query("select (count(m) = 0) from Match m where m.idMatch = ?1")
    Boolean notExistsByIdMatch(String idMatch);

    @Query("select m from Match m where m.idMatch = ?1")
    Optional<Match> findMatchByIdMatch(String IdMatch);

    @Transactional
    @Modifying
    @Query("DELETE FROM Match m WHERE m.dateMatch < CURRENT_TIMESTAMP")
    void deleteMatchStarted();


    @Transactional
    @Modifying
    @Query("delete from Match m where m.linkForeign is null")
    void deleteMatchByLinkForeignNull();
}
