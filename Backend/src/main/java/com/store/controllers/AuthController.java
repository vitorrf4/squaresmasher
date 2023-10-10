package com.store.controllers;

import com.store.models.User;
import com.store.repos.UserRepository;
import com.store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public User checkLogin(@RequestBody User user) {
        user = userRepository.findByName(user.getName());
        System.out.println(user);
        return user;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUpUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createUser(user);

        return ResponseEntity.ok(user);
    }
}
