package com.store;

import com.store.models.*;
import com.store.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.Year;
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
        if (userRepo.existsById(1L)) return;

        StoreStock stock1 = new StoreStock();
        Movie movie = new Movie("test movie", 100, Year.of(2000));
        stock1.addMovieToStock(movie);

        Store store1 = new Store("test store", stock1);

        List<SaleItem> items = new ArrayList<>();
        items.add(new SaleItem(movie, 5));

        User user = new User("user 1", "passwordd 1", store1);
        user = userRepo.save(user);
        System.out.println("Initiliazed user " + user.getId());

    }
}
