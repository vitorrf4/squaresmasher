package com.store.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Sale {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @OneToMany private List<SaleItem> items;
    @OneToOne private Customer customer;
    private Date saleDate;
    private double revenue;
}
