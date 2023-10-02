package com.store.repos;

import com.store.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("SELECT s FROM Sale s LEFT JOIN FETCH s.items where s.id = :id")
    Sale getSaleWithItems(Long id);
}
