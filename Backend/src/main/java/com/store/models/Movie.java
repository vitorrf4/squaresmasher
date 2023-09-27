package com.store.models;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Movie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String movieTitle;
    private Date releaseDate;
    @ElementCollection private List<String> genres;
    private double price;

    public Movie() { }

    public Movie(String movieTitle, Date releaseDate, List<String> genres, double price) {
        this.movieTitle = movieTitle;
        this.releaseDate = releaseDate;
        this.genres = genres;
        this.price = price;
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id) && Objects.equals(movieTitle, movie.movieTitle) && Objects.equals(releaseDate, movie.releaseDate) && Objects.equals(genres, movie.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movieTitle, releaseDate, genres);
    }
}
