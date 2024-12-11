package com.spring.adaimdb.monitor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.adaimdb.models.Film;
import com.spring.adaimdb.models.Round;
import com.spring.adaimdb.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoundProgress {
    private UUID uuid;
    private Film film1;
    private Film film2;
    private int userAnwser;
    private int points;

    public RoundProgress(Film film1, Film film2) {
        uuid = UUID.randomUUID();
        this.film1 = film1;
        this.film2 = film2;
        userAnwser = -1;
        points = -1;
    }

    public Film getFilm1() {
        return film1;
    }

    public void setFilm1(Film film1) {
        this.film1 = film1;
    }

    public Film getFilm2() {
        return film2;
    }

    public void setFilm2(Film film2) {
        this.film2 = film2;
    }

    public int getUserAnwser() {
        return userAnwser;
    }

    public void setUserAnwser(int userAnwser) {
        this.userAnwser = userAnwser;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @JsonIgnore
    public List<Film> getFilmsIds() {
        List<Film> films = new ArrayList<>();
        films.add(film1);
        films.add(film2);
        return films;
    }

    public void calculatePoints() {
        int highestScore = this.film1.getFinalScore() > this.film2.getFinalScore() ? 1
                : this.film2.getFinalScore() > this.film1.getFinalScore() ? 2 : 0;
        // films have same score
        if (highestScore == 0) {
            this.points = 1;
        } else {
            if (highestScore == userAnwser) {
                this.points = 1;
            } else {
                this.points = 0;
            }
        }
    }

    public Round convertRound(User user) {
        return new Round(user, film1, film2, points);
    }
}