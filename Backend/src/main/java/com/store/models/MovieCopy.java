package com.store.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class MovieCopy {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(cascade = CascadeType.ALL) private Movie movie;
    private int copiesAmount;
    private double price;


    public MovieCopy() { }

    public MovieCopy(Movie movie, int copiesAmount, double price) {
        this.movie = movie;
        this.copiesAmount = copiesAmount;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
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
    //TODO documentation
    public void addCopies(int copiesAdded) {
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
        return copiesAmount == movieCopy.copiesAmount && Objects.equals(id, movieCopy.id) && Objects.equals(movie, movieCopy.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movie, copiesAmount);
    }
}
