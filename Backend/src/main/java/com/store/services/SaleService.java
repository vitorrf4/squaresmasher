package com.store.services;

import com.store.models.Sale;
import com.store.repos.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {
    private final SaleRepository repo;

    @Autowired
    public SaleService(SaleRepository repo) {
        this.repo = repo;
    }

    public List<Sale> getAllSales() {
        return repo.findAll();
    }

    public Sale getSale(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Sale makeSale(Sale sale) {
        if (sale == null) return null;

        return repo.save(sale);
    }

    public Sale updateSale(Sale sale) {
        if (sale == null) return null;
        if (!repo.existsById(sale.getId())) return null;
        return repo.save(sale);
    }

    public boolean deleteSale(Long id) {
        Optional<Sale> sale = repo.findById(id);
        if (sale.isEmpty()) return false;
        repo.delete(sale.get());
        return true;
    }
}
