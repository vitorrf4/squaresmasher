package com.store.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieCopy {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("title")
    private String movieTitle;
    private int copiesAmount;
    private double price;


    public MovieCopy() { }

    public MovieCopy(String movieTitle, int copiesAmount, double price) {
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

    public void setPrice(double price) {
        this.price = price;
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
        MovieCopy movieCopy = (MovieCopy) o;
        return copiesAmount == movieCopy.copiesAmount && Objects.equals(id, movieCopy.id) && Objects.equals(movieTitle, movieCopy.movieTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movieTitle, copiesAmount);
    }
}
