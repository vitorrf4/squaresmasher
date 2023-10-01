package com.store.services;

import com.store.models.*;
import com.store.repos.StoreRepository;
import com.store.repos.UserRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RandomSaleService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Autowired
    public RandomSaleService(StoreRepository storeRepository, UserRepository userRepository) {
        this.storeRepository = storeRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Sale generateSale(SaleItem itemsBought, Store userStore) {
        Customer customer = new Customer( new Faker().name().firstName());
        Sale sale = customer.makePurchase(List.of(itemsBought), userStore);
        if (sale == null) return null;

        userStore = storeRepository.save(userStore);
        sale = userStore.getLastSale();

        return sale;
    }

    public Store getUserStore(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) return null;

        return storeRepository.findById(user.get().getStore().getStock().getId()).orElse(null);
    }

    public SaleItem getRandomSaleItem(List<MovieCopy> moviesInStock) {
        int quantityInStock = 0;
        for (int i = 0; i < moviesInStock.size(); i++) {
            quantityInStock += moviesInStock.get(i).getCopiesAmount();
            if (i == moviesInStock.size() - 1 && quantityInStock == 0) {
                return null;
            }
        }

        int randomCopyIndex = getRandomint(0, moviesInStock.size());
        MovieCopy randomCopy = moviesInStock.get(randomCopyIndex);

        int randomCopiesAmount = getRandomint(1, randomCopy.getCopiesAmount());

        return new SaleItem(randomCopy,  randomCopiesAmount);
    }

    public int getRandomint(int min, int max) {
        return (int)(Math.random() * (max - min)) + min;
    }
}
