package com.marko.kladionicajava.repository;

import com.marko.kladionicajava.entitiy.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface EmailRepository extends JpaRepository<Email, String> {

    @Query("select e from Email e where e.email = ?1")
    Optional<Email> existByEmail(String email);


}
