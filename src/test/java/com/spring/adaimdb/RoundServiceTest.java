package com.spring.adaimdb;

import com.spring.adaimdb.models.Film;
import com.spring.adaimdb.models.Round;

import com.spring.adaimdb.monitor.MonitorProgressUsers;
import com.spring.adaimdb.monitor.RoundProgress;
import com.spring.adaimdb.repositories.FilmRepository;
import com.spring.adaimdb.repositories.RoundRepository;
import com.spring.adaimdb.repositories.UserRepository;
import com.spring.adaimdb.services.RoundService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.spring.adaimdb.models.User;

class RoundServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private RoundRepository roundRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RoundService roundService;

    @BeforeEach
    void setUp() {
        Mockito.reset();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMovies_UnansweredRoundExists() {
        String username = "user1";
        RoundProgress roundProgress = new RoundProgress(new Film("Film 1", 7.5f, 1000), new Film("Film 2", 8.0f, 2000));
        when(roundRepository.findCombinations(anyLong(), anyLong())).thenReturn(Collections.emptyList());
        when(filmRepository.findAllByIdIn(anyList())).thenReturn(Arrays.asList(roundProgress.getFilm1(), roundProgress.getFilm2()));
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        MonitorProgressUsers.add(username, roundProgress);
        roundService.numberOfFilms="2";

        RoundProgress result = roundService.getMovies(username);

        assertEquals(roundProgress, result);
    }

    @Test
    void testGetMovies_UnansweredRoundDoesNotExist() {
        String username = "user1";
        RoundProgress roundProgress = new RoundProgress(new Film("Film 1", 7.5f, 1000), new Film("Film 2", 8.0f, 2000));
        when(roundRepository.findCombinations(anyLong(), anyLong())).thenReturn(Collections.emptyList());
        when(filmRepository.findAllByIdIn(anyList())).thenReturn(Arrays.asList(roundProgress.getFilm1(), roundProgress.getFilm2()));
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        roundService.numberOfFilms="2";
        RoundProgress result = roundService.getMovies(username);

        assertNotNull(result);
    }

    @Test
    void testRegistryVote() {
        String username = "user1";
        UUID uuid = UUID.randomUUID();
        int userVote = 1;
        MonitorProgressUsers.getInstance().clear();
        MonitorProgressUsers.getInstance();
        RoundProgress roundProgress = new RoundProgress(
                new Film("Film 1", 7.5f, 1000), new Film("Film 2", 8.0f, 2000));
        roundProgress.setUuid(uuid);
        when(roundRepository.findCombinations(anyLong(), anyLong())).thenReturn(Collections.emptyList());
        when(filmRepository.findAllByIdIn(anyList())).thenReturn(Arrays.asList(roundProgress.getFilm1(), roundProgress.getFilm2()));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User(1L, username)));

        MonitorProgressUsers.add(username, roundProgress);
        Round round = new Round();

        RoundProgress result = roundService.registryVote(uuid, userVote, username);

        assertNotNull(result);
    }

    @Test
    void testUserCanPlay() {
        String username = "user1";
        MonitorProgressUsers.add(username, new RoundProgress(new Film("Film 1", 7.5f, 1000), new Film("Film 2", 8.0f, 2000)));
        MonitorProgressUsers.add(username, new RoundProgress(new Film("Film 3", 6.5f, 800), new Film("Film 4", 9.0f, 3000)));
        MonitorProgressUsers.add(username, new RoundProgress(new Film("Film 5", 8.5f, 1500), new Film("Film 6", 7.0f, 1200)));

        boolean canPlay = roundService.userCanPlay(username);

        assertTrue(canPlay);
    }
}