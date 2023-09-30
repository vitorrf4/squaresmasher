package com.store.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Sale {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private Customer customer;
    @OneToMany(cascade = CascadeType.ALL) @JoinTable(name = "sale_has_items")
    private List<SaleItem> items;
    private LocalDateTime saleDate;
    private double revenue;

    public Sale() { }

    public Sale(List<SaleItem> items, Customer customer) {
        this.items = items;
        this.customer = customer;
        saleDate = LocalDateTime.now();
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

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public double getRevenue() {
        return revenue;
    }

    private void calculateSaleRevenue() {
        for (SaleItem item : items) revenue += item.getItemTotalPrice();
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
