package com.store.dto;

import java.time.LocalDate;
import java.util.Map;

public class SaleDTO {
    private String customerName;
    private Map<String, Integer> moviesBought;
    private double saleTotalPrice;
    private LocalDate saleDate;

    public SaleDTO(String customerName, Map<String, Integer> moviesBought, double saleTotalPrice, LocalDate saleDate) {
        this.customerName = customerName;
        this.moviesBought = moviesBought;
        this.saleTotalPrice = saleTotalPrice;
        this.saleDate = saleDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getSaleTotalPrice() {
        return saleTotalPrice;
    }

    public void setSaleTotalPrice(double saleTotalPrice) {
        this.saleTotalPrice = saleTotalPrice;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public Map<String, Integer> getMoviesBought() {
        return moviesBought;
    }

    public void setMoviesBought(Map<String, Integer> moviesBought) {
        this.moviesBought = moviesBought;
    }

    @Override
    public String toString() {
        return "SaleDTO{" +
                "customerName='" + customerName + '\'' +
                ", moviesBought=" + moviesBought +
                ", saleTotalPrice=" + saleTotalPrice +
                ", saleDate=" + saleDate +
                '}';
    }
}

