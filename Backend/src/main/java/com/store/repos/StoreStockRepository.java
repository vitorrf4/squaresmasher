package com.store.repos;

import com.store.models.StoreStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreStockRepository extends JpaRepository<StoreStock, Long> {
}
