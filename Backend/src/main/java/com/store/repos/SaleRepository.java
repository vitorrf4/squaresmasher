package com.store.repos;

import com.store.models.Sale;
import com.store.models.StoreStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("SELECT s FROM Sale s LEFT JOIN FETCH s.items where s.id = :id")
    public Sale getSaleWithItems(Long id);
}
