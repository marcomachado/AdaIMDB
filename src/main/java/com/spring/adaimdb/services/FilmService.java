package com.spring.adaimdb.services;

import com.spring.adaimdb.models.dto.FilmDTO;
import com.spring.adaimdb.repositories.FilmRepository;
import org.springframework.stereotype.Service;
import com.spring.adaimdb.models.Film;

import java.util.List;

@Service
public class FilmService {

    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<FilmDTO> findByTitle(String filmTitle) {
        return this.filmRepository.findAllByTitleContainingIgnoreCase(filmTitle);
    }

    public Iterable<Film> listAll() {
        return this.filmRepository.findAll();
    }

    public Iterable<Film> saveAllFilms(List<Film> films) {
        return this.filmRepository.saveAll(films);
    }
}
