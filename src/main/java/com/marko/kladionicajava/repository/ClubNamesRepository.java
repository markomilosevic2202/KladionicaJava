package com.marko.kladionicajava.repository;

import com.marko.kladionicajava.entitiy.ClubName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ClubNamesRepository extends JpaRepository<ClubName, String> {

    @Query("select (count(c) = 0) from ClubName c where c.name = ?1")
    boolean existByName(String name);
}
