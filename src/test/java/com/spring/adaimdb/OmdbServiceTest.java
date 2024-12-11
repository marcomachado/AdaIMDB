package com.spring.adaimdb;

import com.spring.adaimdb.imdb.ReadCsv;
import com.spring.adaimdb.imdb.RequestDataFromOmdb;
import com.spring.adaimdb.imdb.model.ImdbMovie;
import com.spring.adaimdb.repositories.FilmRepository;
import com.spring.adaimdb.services.FilmService;
import com.spring.adaimdb.services.OmdbService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class OmdbServiceTest {

    @Mock
    private FilmService filmService;

    @Mock
    private ReadCsv readCsv;

    @Mock
    private RequestDataFromOmdb requestDataFromOmdb;

    @InjectMocks
    private OmdbService omdbService;

    @BeforeEach
    void setUp() {
        Mockito.reset();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateFilmList_Success() throws IOException, InterruptedException {
        List<String> imdbIds = new ArrayList<>();
        omdbService.numberOfFilms = "2";
        imdbIds.add("tt1234567");
        imdbIds.add("tt7654321");

        when(readCsv.getImdbIds(anyInt())).thenReturn(imdbIds);

        ImdbMovie movie1 = new ImdbMovie();
        movie1.setTitle("Movie 1");
        movie1.setImdbRating("7,5");
        movie1.setImdbVotes("1000");

        ImdbMovie movie2 = new ImdbMovie();
        movie2.setTitle("Movie 2");
        movie2.setImdbRating("8,0");
        movie2.setImdbVotes("2000");

        when(requestDataFromOmdb.getMovieFromOmdb("tt1234567")).thenReturn(movie1);
        when(requestDataFromOmdb.getMovieFromOmdb("tt7654321")).thenReturn(movie2);

        omdbService.generateFilmList();

        verify(filmService, times(1)).saveAllFilms(any(List.class));
    }
}

