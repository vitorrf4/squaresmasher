package com.store.controllers;

import com.store.dto.NewUserDTO;
import com.store.models.User;
import com.store.repos.UserRepository;
import com.store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(UserService userService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<User> checkLogin(@RequestBody User user) {
        user = userRepository.findByName(user.getName());
        if (user == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(user);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUpUser(@RequestBody NewUserDTO userDTO) {
        if (userDTO == null) return new ResponseEntity<>(new StringBuilder("Invalid user"), HttpStatus.BAD_REQUEST);

        User user = NewUserDTO.toUser(userDTO);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createUser(user);

        return ResponseEntity.ok(user);
    }
}
