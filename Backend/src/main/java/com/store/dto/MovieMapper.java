package com.store.dto;

import com.store.models.Movie;
import java.time.Year;

public class MovieMapper {
    public static MovieDTO toDTO(Movie domainMovie) {
        String movieTitle = domainMovie.getTitle();
        int copiesAmount = domainMovie.getCopiesAmount();
        double unitPrice = domainMovie.getUnitPrice();
        Year releaseyear = domainMovie.getReleaseYear();
        String posterUrl = domainMovie.getPosterUrl();

        return new MovieDTO(movieTitle, releaseyear, unitPrice, copiesAmount, posterUrl);
    }

    public static Movie toMovie(MovieDTO dto) {
        String movieTitle = dto.movieTitle();
        int copiesAmount = dto.copiesAmount();
        Year releaseyear = dto.releaseYear();
        String posterUrl = dto.posterUrl();

        return new Movie(movieTitle, copiesAmount, releaseyear, posterUrl);

    }
}
