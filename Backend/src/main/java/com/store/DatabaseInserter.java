package com.store;

import com.store.controllers.UserController;
import com.store.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.net.URISyntaxException;

@Component
public class DatabaseInserter implements CommandLineRunner {
    public UserController controller;

    @Autowired
    public DatabaseInserter(UserController controller) {
        this.controller = controller;
    }

    @Override
    public void run(String... args) throws Exception {
//        User user = new User();
//        user.setName("teste");
//
//        System.out.println("Database initiated");
//        try {
//            controller.addUser(user);
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
    }
}
