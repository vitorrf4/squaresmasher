package com.store.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Store {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne(cascade = CascadeType.ALL) @MapsId
    private StoreStock stock;
    @OneToMany(cascade = CascadeType.ALL) @JoinTable(name = "store_makes_sales")
    private List<Sale> sales;
    private double totalRevenue;


    public Store() {
        sales = new ArrayList<>();
        stock  = new StoreStock();
    }

    public Store(String name, StoreStock stock) {
        this.name = name;
        this.stock = stock;
        sales = new ArrayList<>();
        calculateStoreRevenue();
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

    public List<Sale> getSales() {
        return sales;
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

    public Sale makeSale(List<SaleItem> soldItems, Customer customer) {
        for (SaleItem item : soldItems) {
            if (item.getCopiesSold() <= 0) return null;

            Movie movieOnStock = stock.getCopyFromStockByName(item.getMovieCopy());
            if (movieOnStock == null || !movieOnStock.takeCopies(item.getCopiesSold())) return null;

            stock.calculateTotalCopies();
        }

        Sale newSale = new Sale(soldItems, customer);
        sales.add(newSale);
        calculateStoreRevenue();
        return newSale;
    }

    public Sale getLastSale() {
        return sales.get(sales.size() - 1);
    }

    public void calculateStoreRevenue() {
        totalRevenue = 0;
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
