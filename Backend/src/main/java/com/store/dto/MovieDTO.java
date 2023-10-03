package com.store.dto;

import java.time.Year;

public record MovieDTO(
    String movieTitle,
    Year releaseYear,
    double unitPrice,
    int copiesAmount,
    String posterPath
) { }
