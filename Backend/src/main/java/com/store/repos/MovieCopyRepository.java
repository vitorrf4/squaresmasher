package com.store.repos;

import com.store.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieCopyRepository extends JpaRepository<Movie, Long> {
}
