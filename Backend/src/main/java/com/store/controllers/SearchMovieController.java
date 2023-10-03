package com.store.controllers;

import com.store.services.MoviesAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class SearchMovieController {
    private final MoviesAPIService apiService;

    @Autowired
    public SearchMovieController(MoviesAPIService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/movies/search/{query}")
    public ResponseEntity<String> searchMovie(@PathVariable String query) {
        String responseMovie = apiService.searchMovie(query);
        return ResponseEntity.ok(responseMovie);
    }
}
