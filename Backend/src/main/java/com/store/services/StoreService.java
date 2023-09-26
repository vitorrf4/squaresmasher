package com.store.services;

import com.store.models.Store;
import com.store.repos.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {
    public StoreRepository repo;

    @Autowired
    public StoreService(StoreRepository repo) {
        this.repo = repo;
    }

    public List<Store> getAllStores() {
        return repo.findAll();
    }

    public Store getStore(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Store createStore(Store store) {
        if (store == null) return null;

        return repo.save(store);
    }

    public Store changeStore(Store store) {
        if (store == null) return null;
        if (!repo.existsById(store.getId())) return null;

        return repo.save(store);
    }

    public boolean deleteStore(Long id) {
        Optional<Store> store = repo.findById(id);
        if (store.isEmpty()) return false;

        repo.delete(store.get());
        return true;
    }

}
