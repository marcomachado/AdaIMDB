package com.spring.adaimdb.repositories;

import com.spring.adaimdb.models.Round;
import com.spring.adaimdb.models.UserRanking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoundRepository extends CrudRepository<Round, Long> {
    @Query(value = "SELECT r FROM Round r " +
            "WHERE " +
            "r.film1.id=?1 AND r.film2.id=?2 " +
            "OR r.film1.id=?2 AND r.film2.id=?1")
    List<Round> findCombinations(Long f1, Long f2);
}
