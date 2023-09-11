package com.marko.kladionicajava.repository;

import com.marko.kladionicajava.entitiy.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<Users, String> {
    @Query("select u from Users u where u.username = ?1")
    Optional<Users> findByUsername(String username);
}
