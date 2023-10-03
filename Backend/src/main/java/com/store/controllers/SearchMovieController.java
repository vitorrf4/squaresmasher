package com.store.controllers;

import com.store.dto.MovieDTO;
import com.store.dto.MovieMapper;
import com.store.models.Movie;
import com.store.services.MoviesAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class SearchMovieController {
    private final MoviesAPIService apiService;

    @Autowired
    public SearchMovieController(MoviesAPIService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/movies/search/{query}")
    public ResponseEntity<List<MovieDTO>> searchMovie(@PathVariable String query) {
        List<Movie> responseMovies = apiService.searchMovie(query);

        if (responseMovies == null) return ResponseEntity.internalServerError().build();

        List<MovieDTO> moviesDTOs = new ArrayList<>();

        responseMovies.forEach(movie -> {
            moviesDTOs.add(MovieMapper.toDTO(movie));
        });

        return ResponseEntity.ok(moviesDTOs);
    }
}
