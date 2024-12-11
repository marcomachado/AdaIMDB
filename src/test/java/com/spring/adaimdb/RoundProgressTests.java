package com.spring.adaimdb;

import com.spring.adaimdb.models.Film;
import com.spring.adaimdb.monitor.RoundProgress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoundProgressTests {

    @BeforeEach
    void setUp() {
        Mockito.reset();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculatePoints_SameScores_UserCorrect() {
        Film film1 = new Film("Film 1", 1.0F, 4);
        Film film2 = new Film("Film 2", 1.0F, 3);
        RoundProgress roundProgress = new RoundProgress(film1, film2);
        roundProgress.setUserAnwser(1);

        roundProgress.calculatePoints();

        assertEquals(1, roundProgress.getPoints());
    }

    @Test
    void testCalculatePoints_SameScores_UserIncorrect() {
        Film film1 = new Film("Film 1", 1.0F, 4);
        Film film2 = new Film("Film 2", 1.0F, 3);
        RoundProgress roundProgress = new RoundProgress(film1, film2);
        roundProgress.setUserAnwser(2);

        roundProgress.calculatePoints();

        assertEquals(0, roundProgress.getPoints());
    }

    @Test
    void testCalculatePoints_DifferentScores_UserCorrect() {

        Film film1 = new Film("Film 1", 1.0F, 4);
        Film film2 = new Film("Film 2", 1.0F, 3);
        RoundProgress roundProgress = new RoundProgress(film1, film2);
        roundProgress.setUserAnwser(1);

        roundProgress.calculatePoints();

        assertEquals(1, roundProgress.getPoints());
    }

    @Test
    void testCalculatePoints_DifferentScores_UserIncorrect() {
        Film film1 = new Film("Film 1", 1.0F, 4);
        Film film2 = new Film("Film 2", 1.0F, 3);
        RoundProgress roundProgress = new RoundProgress(film1, film2);
        roundProgress.setUserAnwser(2);

        roundProgress.calculatePoints();

        assertEquals(0, roundProgress.getPoints());
    }

    @Test
    void testCalculatePoints_SameScores_BothCorrect() {
        Film film1 = new Film("Film 1", 1.0F, 4);
        Film film2 = new Film("Film 2", 1.0F, 4);
        RoundProgress roundProgress = new RoundProgress(film1, film2);
        roundProgress.setUserAnwser(0);

        roundProgress.calculatePoints();

        assertEquals(1, roundProgress.getPoints());
    }
}
