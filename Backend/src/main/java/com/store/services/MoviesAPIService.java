package com.store.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MoviesAPIService {
    @Value("${tmdb.api.key}")
    public String apiKey;

    public void getMovies() {

    }
}
