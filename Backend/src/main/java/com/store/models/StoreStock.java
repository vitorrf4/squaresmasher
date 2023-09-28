package com.store.models;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class StoreStock {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @OneToMany(cascade = CascadeType.ALL) private List<MovieCopy> copies;
    private int totalCopies;


    public StoreStock() {
        copies = new ArrayList<>();
    }

    public StoreStock(List<MovieCopy> copies) {
        this.copies = copies;
        calculateTotalCopies();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public MovieCopy getCopyFromStock(MovieCopy copy) {
        return copies.stream().filter(
                stockCopy -> stockCopy.getMovie().getMovieTitle()
                        .equals(copy.getMovie().getMovieTitle()))
                        .findFirst().orElse(null);
    }

    public int checkCopyAmount(MovieCopy copy) {
        if (copy.getId() == null) return -1;

        MovieCopy copyOnStock = copies.stream().filter(stockCopy -> stockCopy.getId().equals(copy.getId())).findFirst().orElse(null);
        if (copyOnStock == null) return -1;

        return copyOnStock.getCopiesAmount();
    }

    public void addMovieToStock(MovieCopy movieCopy) {
        copies.add(movieCopy);
        calculateTotalCopies();
    }

    public void calculateTotalCopies() {
        totalCopies = 0;
        for (MovieCopy copy : copies ) totalCopies += copy.getCopiesAmount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreStock that = (StoreStock) o;
        return totalCopies == that.totalCopies && Objects.equals(id, that.id) && Objects.equals(copies, that.copies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, copies, totalCopies);
    }
}
