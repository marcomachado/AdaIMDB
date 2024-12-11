package com.spring.adaimdb.repositories;

import com.spring.adaimdb.models.Film;
import com.spring.adaimdb.models.dto.FilmDTO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FilmRepository extends CrudRepository<Film, Long> {
    List<Film> findAllByIdIn(List<Long> ids);

    List<FilmDTO> findAllByTitleContainingIgnoreCase(String title);
}
