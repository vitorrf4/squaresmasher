package com.store.dto;

import com.store.models.Movie;
import java.time.Year;

public class MovieMapper {
    public static MovieDTO toDTO(Movie domainMovie, String sentPosterPath) {
        String movieTitle = domainMovie.getMovieTitle();
        int copiesAmount = domainMovie.getCopiesAmount();
        double unitPrice = domainMovie.getUnitPrice();
        Year releaseyear = domainMovie.getReleaseYear();

        return new MovieDTO(movieTitle, releaseyear, unitPrice, copiesAmount, sentPosterPath);
    }
}
