package com.marko.kladionicajava.repository;



import com.marko.kladionicajava.entitiy.Adds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AddsRepository extends JpaRepository<Adds, String> {




}
