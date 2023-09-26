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
import java.util.logging.Logger;

@RestController(value = "UserController")
@RequestMapping(path = "/users")
@CrossOrigin // only allow origin from deployed frontend server
public class UserController {
    public UserService service;
    public Logger logger = Logger.getLogger("USER_CONTROLLER_DEBUG");

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<User>> callGetAllUsers() {
        List<User> users = service.getAllUsers();

        if (users == null) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        return ResponseEntity.ok(users);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<?> callGetUserById(@PathVariable Long id) {
        if (service.isIdInvalid(id)) return ResponseEntity.badRequest().build();

        Optional<User> user = service.getUser(id);

        if (user.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(user.get());
    }

    @PostMapping
    public ResponseEntity<User> callCreateUser(@RequestBody User user) {
        if (service.isUserInvalid(user)) return ResponseEntity.badRequest().build();

        User savedUser = service.createUser(user);

        if (savedUser == null) return ResponseEntity.status(HttpStatus.CONFLICT).build();

        return ResponseEntity.created(URI.create("/users/" + savedUser.getId())).body(savedUser); // change created uri for deployed server
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> callChangeUser(@RequestBody User user) {
        if (service.isUserInvalid(user)) return ResponseEntity.badRequest().build();

        User modifiedUser = service.changeUser(user);

        if (modifiedUser == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(modifiedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (service.isIdInvalid(id)) return ResponseEntity.badRequest().build();

        boolean isDeleted = service.deleteUser(id);

        if (!isDeleted) return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();
    }
}
