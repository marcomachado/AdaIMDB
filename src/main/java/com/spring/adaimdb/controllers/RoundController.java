package com.spring.adaimdb.controllers;

import com.spring.adaimdb.models.Round;
import com.spring.adaimdb.models.User;
import com.spring.adaimdb.monitor.MonitorProgressUsers;
import com.spring.adaimdb.monitor.RoundProgress;
import com.spring.adaimdb.services.RoundService;
import com.spring.adaimdb.utils.RequestsUtil;
import com.spring.adaimdb.utils.UuidUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.management.monitor.Monitor;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
public class RoundController {

    private final RoundService roundService;

    public RoundController(RoundService roundService) {
        this.roundService = roundService;
    }

    @GetMapping(value = {"/start"})
    public ResponseEntity<Object> startGame(HttpServletRequest request) {
        String username = RequestsUtil.getNameUserPrincipal(request);
        if (!roundService.userCanPlay(username)) {
            String mensagemErro = "The user missed 3 questions. Cannot play anymore";
            return new ResponseEntity<>(mensagemErro, HttpStatus.FORBIDDEN);
        }

        RoundProgress rp = roundService.getMovies(username);
        return new ResponseEntity<>(rp, HttpStatus.OK);
    }

    @GetMapping(value = {"/stop"})
    public ResponseEntity<Object> stopGame(HttpServletRequest request) {
        String username = RequestsUtil.getNameUserPrincipal(request);
        MonitorProgressUsers.clearUser(username);

        String mensagem = "Round stopped";
        return new ResponseEntity<>(mensagem, HttpStatus.OK);
    }

    @PostMapping("vote/{userVote}")
    public Object registryVote(@PathVariable int userVote, @RequestBody String uuid, HttpServletRequest request) {
        String username = RequestsUtil.getNameUserPrincipal(request);
        uuid = UuidUtil.clean(uuid);

        UUID uuidRound = UUID.fromString(uuid);
        if (!roundService.userCanPlay(username)) {
            String mensagemErro = "The user missed 3 questions. Cannot play anymore";
            return new ResponseEntity<>(mensagemErro, HttpStatus.FORBIDDEN);
        }
        if (userVote == 1 || userVote == 2) {

            try {
                RoundProgress roundProgress = roundService.registryVote(uuidRound, userVote, username);
                String msg = "Wrong answer";
                if(roundProgress.getPoints() == 1)
                    msg = "Correct answer";

                URI location = URI.create("/start");
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(location);

                return new ResponseEntity<>(msg, headers, HttpStatus.CREATED);
            } catch (IllegalArgumentException iae) {
                return new ResponseEntity<>(iae.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            String mensagemErro = "Non-existent option. Vote 1 or 2";
            return new ResponseEntity<>(mensagemErro, HttpStatus.BAD_REQUEST);
        }
    }
}
