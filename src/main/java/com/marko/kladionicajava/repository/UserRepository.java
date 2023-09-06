package com.marko.kladionicajava.repository;

import com.marko.kladionicajava.entitiy.Quotas;
import com.marko.kladionicajava.entitiy.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<Users, String> {
}
