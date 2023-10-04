package com.store.models;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class StoreStock {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @OneToMany(cascade = CascadeType.ALL) private List<Movie> movies;
    private int copiesTotal;


    public StoreStock() {
        movies = new ArrayList<>();
    }

    public StoreStock(List<Movie> movies) {
        this.movies = movies;
        calculateTotalCopies();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCopiesTotal() {
        return copiesTotal;
    }

    public Movie getCopyFromStockByName(Movie copy) {
        return movies.stream().filter(
                stockCopy -> stockCopy.getMovieTitle()
                        .equals(copy.getMovieTitle()))
                        .findFirst().orElse(null);
    }

    public void addMovieToStock(Movie movie) {
        if (getCopyFromStockByName(movie) != null) {
            System.out.println("movie " + movie.getMovieTitle() + " already in stock");
            getCopyFromStockByName(movie).addCopies(movie.getCopiesAmount());
            calculateTotalCopies();
            return;
        }

        movies.add(movie);
        calculateTotalCopies();
    }

    public void calculateTotalCopies() {
        copiesTotal = 0;
        for (Movie copy : movies) copiesTotal += copy.getCopiesAmount();
    }

    public List<Movie> getAllCopies() {
        return movies.parallelStream().toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreStock that = (StoreStock) o;
        return copiesTotal == that.copiesTotal && Objects.equals(id, that.id) && Objects.equals(movies, that.movies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movies, copiesTotal);
    }
}
