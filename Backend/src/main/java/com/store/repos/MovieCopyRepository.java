package com.store.repos;

import com.store.models.MovieCopy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieCopyRepository extends JpaRepository<MovieCopy, Long> {
}
