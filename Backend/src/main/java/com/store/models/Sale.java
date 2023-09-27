package com.store.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
public class Sale {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @OneToOne private Customer customer;
    @OneToMany private List<SaleItem> items;
    @ManyToOne(cascade = CascadeType.ALL) private Store store;
    private LocalDate saleDate;
    private double revenue;


    public Sale() { }

    public Sale(List<SaleItem> items, Customer customer, Store store) {
        this.items = items;
        this.customer = customer;
        this.store = store;
        saleDate = LocalDate.now();
        calculateSaleRevenue();
    }

    public Long getId() {
        return id;
    }

    public List<SaleItem> getItems() {
        return items;
    }

    public void setItems(List<SaleItem> items) {
        this.items = items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public double getRevenue() {
        return revenue;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    private void calculateSaleRevenue() {
        double revenue = 0;
        for (SaleItem item : items) revenue += item.getItemTotalPrice();

        this.revenue = revenue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return Double.compare(revenue, sale.revenue) == 0 && Objects.equals(id, sale.id) && Objects.equals(items, sale.items) && Objects.equals(customer, sale.customer) && Objects.equals(saleDate, sale.saleDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, items, customer, saleDate, revenue);
    }

}
