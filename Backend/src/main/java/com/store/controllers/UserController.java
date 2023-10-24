package com.store.controllers;

import com.store.models.User;
import com.store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController(value = "UserController")
@RequestMapping(path = "/users")
@CrossOrigin(origins = {"https://vitorrf4.github.io/squaresmasher", "http://localhost:4200"})
public class UserController {
    public final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = service.getAllUsers();
        if (users == null) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        return ResponseEntity.ok(users);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        if (service.isIdInvalid(id)) return ResponseEntity.badRequest().build();

        Optional<User> user = service.getUser(Long.parseLong(id));
        if (user.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(user.get());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (service.isUserInvalid(user)) return ResponseEntity.badRequest().build();

        User savedUser = service.saveUser(user);
        if (savedUser == null) return ResponseEntity.status(HttpStatus.CONFLICT).build();

        return ResponseEntity.created(URI.create("/users/" + savedUser.getId())).body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> changeUser(@RequestBody User user) {
        if (service.isUserInvalid(user)) return ResponseEntity.badRequest().build();

        User modifiedUser = service.changeUser(user);
        if (modifiedUser == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(modifiedUser);
    }
}
