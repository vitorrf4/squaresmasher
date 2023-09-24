package com.store.models;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Store {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @OneToOne private StoreStock stock;
    @OneToMany private List<Sale> sales;
    private String name;
    private double totalRevenue;


    public Store() { }

    public Store(Long id, StoreStock stock, List<Sale> sales, String name, double totalRevenue) {
        this.id = id;
        this.stock = stock;
        this.sales = sales;
        this.name = name;
        this.totalRevenue = totalRevenue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StoreStock getStock() {
        return stock;
    }

    public void setStock(StoreStock stock) {
        this.stock = stock;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return Double.compare(totalRevenue, store.totalRevenue) == 0 && Objects.equals(id, store.id) && Objects.equals(stock, store.stock) && Objects.equals(sales, store.sales) && Objects.equals(name, store.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stock, sales, name, totalRevenue);
    }
}
