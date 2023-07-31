package com.marko.kladionicajava.repository;

import com.marko.kladionicajava.entitiy.ClubName;
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



    @Query("select c from ClubName c where c.maxbetName is null")
    List<ClubName> findAllByMaxbetNameIsNull();

    @Query("select c from ClubName c where c.meridianName is null")
    List<ClubName> findAllByMeridianNameIsNull();

    @Query("select c from ClubName c where c.mozzartName is null")
    List<ClubName> findAllByMozzartNameIsNull();
}
