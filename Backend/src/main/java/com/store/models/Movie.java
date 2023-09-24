package com.store.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Movie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String movieTitle;
    private Date releaseDate;
    @ElementCollection private List<String> genres;
}
