package com.store.models;

import jakarta.persistence.*;

@Entity
public class SaleItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne private Movie movie;
    @ManyToOne private Sale sale;
    private int copiesSold;
    private double itemTotalPrice;
}
