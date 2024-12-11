package com.spring.adaimdb.controllers;

import com.spring.adaimdb.models.UserRanking;
import com.spring.adaimdb.services.RankingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping("/viewranking")
    public ResponseEntity<List<UserRanking>> generateRanking() {
        List<UserRanking> ranking = rankingService.getRankingAllUsers();
        return new ResponseEntity<>(ranking, HttpStatus.OK);
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<UserRanking>> getRanking() {
        List<UserRanking> ranking = rankingService.getRanking();
        return new ResponseEntity<>(ranking, HttpStatus.OK);
    }
}
