package com.spring.adaimdb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Film {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    @JsonIgnore
    private Float rating;
    @JsonIgnore
    private int votes;

    public Film() {
    }

    public Film(Long id) {
        this.id = id;
    }

    public Film(String title, Float rating, int votes) {
        this.title = title;
        this.rating = rating;
        this.votes = votes;
    }

    public Float calculateFinalScore() {
        return this.rating * this.votes;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @JsonIgnore
    public Float getFinalScore(){
        return this.rating * this.votes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(id, film.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
