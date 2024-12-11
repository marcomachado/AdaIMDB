package com.spring.adaimdb.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "rounds")
public class Round {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @NotNull
    private User user;

    @ManyToOne
    @NotNull
    private Film film1;

    @ManyToOne
    @NotNull
    private Film film2;

    private int points;

    public Round() {
    }

    public Round(User user, Film film1, Film film2, int points) {
        this.user = user;
        this.film1 = film1;
        this.film2 = film2;
        this.points = points;
    }

    public Round(Film film1, Film film2) {
        this.film1 = film1;
        this.film2 = film2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int userVote) {
        this.points = userVote;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
