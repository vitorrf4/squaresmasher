package com.store.controllers;

import com.store.models.User;
import com.store.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "users")
@CrossOrigin
public class UserController {
    public UserRepo repo;
    public Logger logger = Logger.getLogger("USER_CONTROLLER_DEBUG");

    @Autowired
    public UserController(UserRepo repo) {
        this.repo = repo;
    }

    @GetMapping(path="list")
    ResponseEntity<List<User>> getAllUsers() {
        logger.info("User list requested at " + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());

        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping(path="search/{id}")
    ResponseEntity<?> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(repo.findById(id));
    }

    @PostMapping("add")
    public ResponseEntity<User> addUser(@RequestBody User user) throws URISyntaxException {
        User createdUser = repo.save(user);

        logger.info("User " + createdUser.getId() + " created at " + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());

        return ResponseEntity.created(new URI("user/" + createdUser.getId())).body(createdUser);
    }

    @PutMapping("change")
    ResponseEntity<User> changeUser(@RequestBody User user) {
        User modifiedUser = repo.save(user);

        return ResponseEntity.ok(modifiedUser);
    }

    @DeleteMapping("delete/{id}")
    ResponseEntity<?> deleteUser(@PathVariable Long id) {
        logger.info("User " + id + " delete requested at " + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());

        repo.deleteById(id);

        return ResponseEntity.noContent().build();
    }


}
