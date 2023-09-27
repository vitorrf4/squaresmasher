package com.store.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class MovieCopy {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne private Movie movie;
    private int copiesAmount;


    public MovieCopy() { }

    public MovieCopy(Movie movie, int copiesAmount) {
        this.movie = movie;
        this.copiesAmount = copiesAmount;
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

    public int getCopiesAmount() {
        return copiesAmount;
    }

    public void setCopiesAmount(int copiesAmount) {
        this.copiesAmount = copiesAmount;
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
