package com.store.dto;

import java.time.Year;

public record MovieDTO(
    String movieTitle,
    int copiesAmount,
    String posterPath,
    Year releaseYear
) { }
