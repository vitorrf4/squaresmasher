package com.store.services;

import com.store.models.*;
import com.store.repos.StoreRepository;
import com.store.repos.StoreStockRepository;
import com.store.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.datafaker.Faker;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RandomSaleService {
    private final Faker faker = new Faker();
    private final StoreStockRepository stockRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Autowired
    public RandomSaleService(StoreStockRepository stockRepository, StoreRepository storeRepository, UserRepository userRepository) {
        this.stockRepository = stockRepository;
        this.storeRepository = storeRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Sale generateSale(Long id) {
        //TODO break down this function
        Customer customer = new Customer(faker.name().firstName());
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) return null;
        Store userStore = user.get().getStore();
        List<MovieCopy> moviesInStock = stockRepository.findById(userStore.getStock().getId()).get().getAllCopies();


        int randomCopyIndex = (int)(Math.random() + (moviesInStock.size() - 1));

        SaleItem randomMovie = new SaleItem(moviesInStock.get(randomCopyIndex), 1);

        List<SaleItem> items = new ArrayList<>();
        items.add(randomMovie);

        Sale sale = customer.makePurchase(items, userStore);
        if (sale == null) return null;
        userStore = storeRepository.save(userStore);
        sale = userStore.getLastSale();

        return sale;
    }
}
