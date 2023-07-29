package com.marko.kladionicajava.repository;

import com.marko.kladionicajava.entitiy.ClubName;
import com.marko.kladionicajava.entitiy.Match;
import com.marko.kladionicajava.entitiy.NameBetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClubNamesRepository extends JpaRepository<ClubName, String> {

    @Query("select (count(c) = 0) from ClubName c where c.maxbetName = ?1")
    boolean existByName(String name);



    @Query("select c from ClubName c where c.foreignName is null")
    List<ClubName> findAllByForeignNameIsNull();

    @Query("select c from ClubName c where c.maxbetName = ?1")
    ClubName findByMaxbetName(String maxbetName);
}
