package com.spring.adaimdb.services;

import com.spring.adaimdb.models.UserRanking;
import com.spring.adaimdb.repositories.RankingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankingService {

    private final RankingRepository rankingRepository;

    public RankingService(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    public List<UserRanking> getRankingAllUsers() {
        return (List<UserRanking>) rankingRepository.findAll();
    }

    public List<UserRanking> getRanking() {
        return rankingRepository.generateRanking();
    }
}
