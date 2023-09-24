package com.store.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class StoreStock {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @OneToMany private List<MovieCopy> copies;
    private int totalCopies;
}
