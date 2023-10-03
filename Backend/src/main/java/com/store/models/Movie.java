package com.store.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.Year;
import java.util.Objects;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("title")
    private String movieTitle;
    private int copiesAmount;
    private double price;
    private Year releaseYear;

    public Movie() { }

    public Movie(Long id, String movieTitle, int copiesAmount, Year releaseYear) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.copiesAmount = copiesAmount;
        this.releaseYear = releaseYear;
        calculatePrice();
    }

    public Movie(String movieTitle, int copiesAmount, double price) {
        this.movieTitle = movieTitle;
        this.copiesAmount = copiesAmount;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movie) {
        this.movieTitle = movie;
    }

    public double getPrice() {
        return price;
    }

    public void calculatePrice() {
        String currentYear = Year.now().toString();
        if (currentYear.equals(releaseYear.toString())) {
            price = 20; // if it's a new release(year of release is the current year) price is a fixed $20
            return;
        }

        int currentDecade = Integer.parseInt(currentYear.substring(0, 2));
        int movieReleaseDecade = Integer.parseInt(releaseYear.toString().substring(0, 2));

        int decadePrice = currentDecade - movieReleaseDecade;

        price = 5 + decadePrice;
    }

    public Year getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Year releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getCopiesAmount() {
        return copiesAmount;
    }

    public void addCopies(int copiesAdded) {
        //TODO test for negative numbers
        copiesAmount += copiesAdded;
    }

    public boolean takeCopies(int copiesTaken) {
        if (copiesTaken > copiesAmount) return false;

        copiesAmount -= copiesTaken;
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return copiesAmount == movie.copiesAmount && Objects.equals(id, movie.id) && Objects.equals(movieTitle, movie.movieTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movieTitle, copiesAmount);
    }
}
