package com.marko.kladionicajava.repository;

import com.marko.kladionicajava.entitiy.ClubName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClubNamesRepository extends JpaRepository<ClubName, String> {
}
