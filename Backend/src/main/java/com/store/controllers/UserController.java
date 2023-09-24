package com.store.controllers;

import com.store.models.User;
import com.store.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
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
        if(repo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(repo.findById(id));
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) throws URISyntaxException {
        if (user == null || user.getName().isEmpty() || user.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (repo.findByName(user.getName()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        User createdUser = repo.save(user);

        return ResponseEntity.created(new URI("/users/" + createdUser.getId())).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> changeUser(@RequestBody User user) {
        User modifiedUser = repo.save(user);

        return ResponseEntity.ok(modifiedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        repo.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
