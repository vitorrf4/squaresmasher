package com.store.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("title")
    private String movieTitle;
    @JsonProperty("release_date")
    private int releaseYear;

    public Movie() { }

    public Movie(String movieTitle, int releaseYear) {
        this.movieTitle = movieTitle;
        this.releaseYear = releaseYear;
    }

    public Long getId() {
        return id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseDate) {
        this.releaseYear = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id) && Objects.equals(movieTitle, movie.movieTitle) && Objects.equals(releaseYear, movie.releaseYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movieTitle, releaseYear);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", movieTitle='" + movieTitle + '\'' +
                ", releaseYear=" + releaseYear +
                '}';
    }
}
