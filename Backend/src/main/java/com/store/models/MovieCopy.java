package com.store.models;

import jakarta.persistence.*;

@Entity
public class MovieCopy {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne private Movie movie;
    private int copiesAmount;
}
