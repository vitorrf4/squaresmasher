package com.store.models;

import jakarta.persistence.*;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;

@Entity
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    public Customer() { }

    public Customer(String name) {
        if (name == null || name.isEmpty() || name.isBlank())
            throw new InvalidParameterException();

        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty() || name.isBlank())
            throw new InvalidParameterException();

        this.name = name;
    }

    public Sale makePurchase(List<SaleItem> purchasedItems, Store store) {
        if (store == null) return null;

        return store.makeSale(purchasedItems, this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
