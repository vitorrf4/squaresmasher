package com.store;

import com.store.models.Store;
import com.store.models.StoreStock;
import com.store.models.User;
import com.store.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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


        Store store1 = new Store("test store", new StoreStock());

        User user = new User("user 1", "passwordd 1", store1);
        user = userRepo.save(user);
        System.out.println("Initiliazed user " + user.getId());

    }
}
