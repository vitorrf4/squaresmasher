package com.store.models;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Sale {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @OneToOne private Customer customer;
    @OneToMany private List<SaleItem> items;
    @OneToOne private Store store;
    private Date saleDate;
    private double revenue;


    public Sale() { }

    public Sale(Long id, List<SaleItem> items, Customer customer, Date saleDate, double revenue) {
        this.id = id;
        this.items = items;
        this.customer = customer;
        this.saleDate = saleDate;
        this.revenue = revenue;
    }

    public Long getId() {
        return id;
    }

//    public void setId(Long id) {
//        this.id = id;
//    }

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

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
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
