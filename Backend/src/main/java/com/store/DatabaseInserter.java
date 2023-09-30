package com.store;

import com.store.models.*;
import com.store.repos.*;
import com.store.services.*;
import com.store.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseInserter implements CommandLineRunner {
    public final UserRepository userRepo;

    @Autowired
    public DatabaseInserter(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        //TODO rollback failed initializations
//        if (userRepo.existsById(1L)) return;


        Movie movie1 = new Movie("House of Leaves", Date.valueOf("2000-08-01"),List.of("Horror"));
        MovieCopy copy1 = new MovieCopy(movie1, 5, 20);
        StoreStock stock1 = new StoreStock();
        stock1.addMovieToStock(copy1);

        Store store1 = new Store("test store", stock1);

        List<SaleItem> items = new ArrayList<>();
        items.add(new SaleItem(copy1, 5));

        User user = userRepo.findById(13L).get();
        user.setStore(store1);
        userRepo.save(user);

        //TODO check that stock has only one movie_copy of a given movie
    }
}
