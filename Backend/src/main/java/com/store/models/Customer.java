package com.store.models;

import jakarta.persistence.*;

@Entity
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    public Customer() { }

    public Customer(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
