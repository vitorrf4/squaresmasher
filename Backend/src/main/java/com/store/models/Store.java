package com.store.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Store {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @OneToOne private User user;
    @OneToOne private StoreStock stock;
    @OneToMany private List<Sale> sales;
    private String name;
    private double totalRevenue;
}
