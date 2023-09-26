package com.store.services;

import com.store.models.SaleItem;
import com.store.repos.SaleItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SaleItemService {
    private final SaleItemRepository repo;

    @Autowired
    public SaleItemService(SaleItemRepository repo) {
        this.repo = repo;
    }

    public List<SaleItem> getAllSales() {
        return repo.findAll();
    }

    public SaleItem getSaleItem(Long id) {
        return repo.findById(id).orElse(null);
    }

    public SaleItem createSaleItem(SaleItem saleItem) {
        if (saleItem == null) return null;
        return repo.save(saleItem);
    }

    public SaleItem updateSaleItem(SaleItem saleItem) {
        if (saleItem == null) return null;
        if (!repo.existsById(saleItem.getId())) return null;
        return repo.save(saleItem);
    }

    public boolean deleteSaleItem(Long id) {
        Optional<SaleItem> saleItem = repo.findById(id);
        if (saleItem.isEmpty()) return false;
        repo.delete(saleItem.get());
        return true;
    }

}
