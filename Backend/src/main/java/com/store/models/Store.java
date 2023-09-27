package com.store.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Store {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String name;
    @OneToOne private StoreStock stock;
    @OneToMany(cascade = CascadeType.ALL)  private List<Sale> sales;
    private double totalRevenue;


    public Store() {
        sales = new ArrayList<>();
    }

    public Store(String name, StoreStock stock, List<Sale> sales) {
        this.name = name;
        this.stock = stock;
        this.sales = sales;
        calculateRevenue();
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

    public void calculateRevenue() {
        for (Sale sale : sales) totalRevenue += sale.getRevenue();
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
