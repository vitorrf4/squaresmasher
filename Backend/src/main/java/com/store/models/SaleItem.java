package com.store.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class SaleItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne private Movie movie;
    private int copiesSold;
    private double itemTotalPrice;

    public SaleItem() { }

    public SaleItem(Movie movie, int copiesSold) {
        this.movie = movie;
        this.copiesSold = copiesSold;
        calculateTotalPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie getMovieCopy() {
        return movie;
    }

    public void setMovieCopy(Movie movie) {
        this.movie = movie;
    }

    public int getCopiesSold() {
        return copiesSold;
    }

    public void setCopiesSold(int copiesSold) {
        this.copiesSold = copiesSold;
    }

    public double getItemTotalPrice() {
        return itemTotalPrice;
    }

    public void calculateTotalPrice() {
        this.itemTotalPrice = movie.getUnitPrice() * copiesSold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleItem saleItem = (SaleItem) o;
        return copiesSold == saleItem.copiesSold && Double.compare(itemTotalPrice, saleItem.itemTotalPrice) == 0 && Objects.equals(id, saleItem.id) && Objects.equals(movie, saleItem.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movie, copiesSold, itemTotalPrice);
    }
}
