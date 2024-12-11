package com.spring.adaimdb.controllers;

import com.spring.adaimdb.models.Film;
import com.spring.adaimdb.models.dto.FilmDTO;
import com.spring.adaimdb.services.FilmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<List<FilmDTO>> findByTitle(@PathVariable String title) {
        List<FilmDTO> films = filmService.findByTitle(title);
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    @GetMapping("/listall")
    public ResponseEntity<Iterable<Film>> findByTitle() {
        Iterable<Film> films = filmService.listAll();
        return new ResponseEntity<>(films, HttpStatus.OK);
    }
}
