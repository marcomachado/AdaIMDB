package com.spring.adaimdb.services;

import com.spring.adaimdb.imdb.ReadCsv;
import com.spring.adaimdb.imdb.RequestDataFromOmdb;
import com.spring.adaimdb.imdb.model.ImdbMovie;
import com.spring.adaimdb.models.Film;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Configuration
@PropertySource("classpath:application.properties")
public class OmdbService {
    private final FilmService filmService;

    @Value("${numberOfFilms:20}")
    public String numberOfFilms;

    ReadCsv readCsv;

    RequestDataFromOmdb requestDataFromOmdb;

    public OmdbService(FilmService filmService, ReadCsv readCsv, RequestDataFromOmdb requestDataFromOmdb) {
        this.filmService = filmService;
        this.readCsv = readCsv;
        this.requestDataFromOmdb = requestDataFromOmdb;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void generateFilmList() throws IOException, InterruptedException {
        List<String> imdbIds = readCsv.getImdbIds(Integer.parseInt(numberOfFilms));
        List<Film> filmsToSave = new ArrayList<>();

        for (String id : imdbIds) {
            ImdbMovie movieFromOmdb = requestDataFromOmdb.getMovieFromOmdb(id);
            String title = movieFromOmdb.getTitle();
            String imdbRating = movieFromOmdb.getImdbRating().replace(",", ".");
            String imdbVotes = movieFromOmdb.getImdbVotes().replace(",", "");

            Film f = new Film(title, Float.parseFloat(imdbRating), Integer.parseInt(imdbVotes));
            filmsToSave.add(f);
        }
        filmService.saveAllFilms(filmsToSave);
    }
}
