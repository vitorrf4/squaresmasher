package com.store.controllers;

import com.store.models.User;
import com.store.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/users")
@CrossOrigin // only allow origin from deployed frontend server
public class UserController {
    public UserRepo repo;
    public Logger logger = Logger.getLogger("USER_CONTROLLER_DEBUG");

    @Autowired
    public UserController(UserRepo repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        if(!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(repo.findById(id));
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) throws URISyntaxException {
        if (user == null || user.getName().isEmpty() || user.getName().isBlank()
                || user.getPassword().isEmpty() || user.getPassword().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        if (repo.findByName(user.getName()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        User createdUser = repo.save(user);

        return ResponseEntity.created(new URI("/users/" + createdUser.getId())).body(createdUser); // change created uri for deployed server
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> changeUser(@PathVariable Long id, @RequestBody User user) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        if (user.getName().isEmpty() || user.getName().isBlank()
                || user.getPassword().isBlank() || user.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        User modifiedUser = repo.save(user);

        return ResponseEntity.ok(modifiedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
