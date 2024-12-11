package com.spring.adaimdb.services;

import com.spring.adaimdb.models.*;
import com.spring.adaimdb.monitor.MonitorProgressUsers;
import com.spring.adaimdb.monitor.RoundProgress;
import com.spring.adaimdb.repositories.FilmRepository;
import com.spring.adaimdb.repositories.RoundRepository;
import com.spring.adaimdb.repositories.UserRepository;
import com.spring.adaimdb.utils.RandomIds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Configuration
@PropertySource("classpath:application.properties")
public class RoundService {
    private final FilmRepository filmRepository;
    private final RoundRepository roundRepositoy;
    private final UserRepository userRepository;

    private Map<String, List<RoundProgress>> monitor;

    @Value("${numberOfFilms:20}")
    public String numberOfFilms;

    public RoundService(FilmRepository filmRepository, RoundRepository roundRepositoy,
                        UserRepository userRepository) {
        this.filmRepository = filmRepository;
        this.roundRepositoy = roundRepositoy;
        this.userRepository = userRepository;
        this.monitor = MonitorProgressUsers.getInstance();
    }

    public RoundProgress getMovies(String username) {
        List<RoundProgress> unansweredRoundFilms = MonitorProgressUsers.getUnansweredRound(username);
        if (!unansweredRoundFilms.isEmpty()) {
            return unansweredRoundFilms.get(0);
        }
        List<Film> films;
        do {
            RandomIds result = this.generateUniqueFilmCombination();

            films = filmRepository.findAllByIdIn(Arrays.asList(result.id1(), result.id2()));
        }while (films.size() < 2);

        RoundProgress rp = new RoundProgress(films.get(0), films.get(1));
        MonitorProgressUsers.add(username, rp);

        return rp;
    }

    private RandomIds generateUniqueFilmCombination() {
        RandomIds result;
        List<Round> combinations = List.of();
        do {
            result = this.getRandomIds();
            Long id1 = result.id1();
            Long id2 = result.id2();
            if (id1.equals(id2))
                continue;

            combinations = roundRepositoy.findCombinations(id1, id2);
        } while (!combinations.isEmpty());
        return result;
    }

    private RandomIds getRandomIds () {
        Random random = new Random();

        Long id1 = random.nextLong(Long.parseLong(numberOfFilms)) + 1;
        Long id2 = random.nextLong(Long.parseLong(numberOfFilms)) + 1;
        RandomIds result = new RandomIds(id1, id2);
        return result;
    }

    public Round save(Round round) {
        return roundRepositoy.save(round);
    }

    public RoundProgress registryVote(UUID uuid, int userVote, String username) {
        try {
            RoundProgress roundProgress = updateRoundPoints(username, uuid, userVote);
            Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username).orElseThrow(() ->
                    new IllegalArgumentException("User not found " + username)));
            Round round = roundProgress.convertRound(user.get());
            round = save(round);
            return roundProgress;
        } catch (IllegalArgumentException iae) {
            throw new IllegalArgumentException(iae.getMessage());
        }
    }

    private RoundProgress updateRoundPoints(String username, UUID uuid, int userVote) {
        Optional<RoundProgress> roundOp = MonitorProgressUsers.getRoundFromUserAndUuid(username, uuid);
        if (!roundOp.isPresent()) {
            throw new IllegalArgumentException("Round not found or already voted for username" + username + " and UUID " + uuid);
        }
        RoundProgress round = roundOp.get();

        Long idFilm1 = round.getFilm1().getId();
        Long idFilm2 = round.getFilm2().getId();
        List<Film> films = filmRepository.findAllByIdIn(Arrays.asList(idFilm1, idFilm2));
        round.setFilm1(films.get(0));
        round.setFilm2(films.get(1));
        round.setUserAnwser(userVote);
        round.calculatePoints();
        return round;
    }

    public boolean userCanPlay(String username) {
        return MonitorProgressUsers.countWrongAnwsers(username) < 3;
    }
}
