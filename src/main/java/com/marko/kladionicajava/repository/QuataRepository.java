package com.marko.kladionicajava.repository;



import com.marko.kladionicajava.entitiy.Quota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuataRepository extends JpaRepository<Quota, String> {




}
