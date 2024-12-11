package com.spring.adaimdb.repositories;

import com.spring.adaimdb.models.UserRanking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RankingRepository extends CrudRepository<UserRanking, Long> {
    @Query(value ="SELECT ROW_NUMBER() OVER() as id_fake, " +
            "s1.id as user_id, s1.total_1 * 100.0 / tr.total as score_final " +
            "FROM (SELECT u.id, COALESCE((SELECT COUNT(*) FROM ROUNDS r " +
            "WHERE r.user_id = u.id AND r.points = 1), 0) AS total_1 FROM USERS u) s1 " +
            "JOIN (SELECT r.user_id, COUNT(*) AS total FROM ROUNDS r GROUP BY r.user_id) tr " +
            "ON s1.id = tr.user_id ORDER BY score_final DESC", nativeQuery = true)
    List<UserRanking> generateRanking();
}