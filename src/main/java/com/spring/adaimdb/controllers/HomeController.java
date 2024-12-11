package com.spring.adaimdb.controllers;

import com.spring.adaimdb.models.Film;
import com.spring.adaimdb.models.UserRanking;
import com.spring.adaimdb.monitor.MonitorProgressUsers;
import com.spring.adaimdb.services.RankingService;
import com.spring.adaimdb.services.RoundService;
import com.spring.adaimdb.utils.RequestsUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class HomeController {

    @GetMapping(value = {"/", "/home"})
    public ResponseEntity<String> home(HttpServletRequest request) {

        return new ResponseEntity<>("Welcome to AdaImdb", HttpStatus.OK);
    }
}
