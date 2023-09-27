package com.store.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class SaleItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne private Movie movie;
    @ManyToOne private Sale sale;
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

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
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
        this.itemTotalPrice = movie.getPrice() * copiesSold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleItem saleItem = (SaleItem) o;
        return copiesSold == saleItem.copiesSold && Double.compare(itemTotalPrice, saleItem.itemTotalPrice) == 0 && Objects.equals(id, saleItem.id) && Objects.equals(movie, saleItem.movie) && Objects.equals(sale, saleItem.sale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movie, sale, copiesSold, itemTotalPrice);
    }
}
