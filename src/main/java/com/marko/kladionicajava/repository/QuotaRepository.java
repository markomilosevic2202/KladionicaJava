package com.marko.kladionicajava.repository;



import com.marko.kladionicajava.entitiy.Match;
import com.marko.kladionicajava.entitiy.Quotas;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuotaRepository extends JpaRepository<Quotas, String> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Quotas q WHERE q.matches IN (SELECT m FROM Match m WHERE m.dateMatch < CURRENT_TIMESTAMP)")
    void deleteAllMatchHaveStarted();




    @Query("SELECT DISTINCT q.timeView FROM Quotas q")
    List<String> findAllDistinctByNumberOfView();

    @Query("select q from Quotas q where q.timeView = ?1")
    List<Quotas> findAllByNumber(String timeView);
}
