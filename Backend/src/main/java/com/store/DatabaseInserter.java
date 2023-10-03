package com.store;

import com.store.models.*;
import com.store.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    public void run(String... args) {
        //TODO rollback failed initializations

//        MovieCopy copy1 = new MovieCopy("lord of flies", 100, 25);
//        MovieCopy copy2 = new MovieCopy("the lobster", 100, 20);
//        MovieCopy copy3 = new MovieCopy("alps", 100, 30);
//        StoreStock stock1 = new StoreStock();
//        stock1.addMovieToStock(copy1);
//        stock1.addMovieToStock(copy2);
//        stock1.addMovieToStock(copy3);
//
//        Store store1 = new Store("test store", stock1);
//
//        List<SaleItem> items = new ArrayList<>();
//        items.add(new SaleItem(copy1, 5));
//
//        User user = new User("user 1", "passwordd 1", store1);
//        user = userRepo.save(user);
//        System.out.println("Initiliazed user " + user.getId());


        //TODO check that stock has only one movie_copy of a given movie
    }
}
