package com.store.controllers;

import com.store.dto.NewUserDTO;
import com.store.models.User;
import com.store.security.AuthenticationRequest;
import com.store.security.AuthenticationResponse;
import com.store.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@CrossOrigin(origins = {"https://vitorrf4.github.io", "http://localhost:4200"})
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse authenticationResponse = authService.setJwtToken(authenticationRequest);
        if (authenticationResponse == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUpUser(@RequestBody NewUserDTO userDTO) {
        if (userDTO == null) return ResponseEntity.badRequest().build();

        if (authService.isUsernameAlreadyTaken(userDTO.name()))
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        User user = authService.createUser(userDTO);

        return ResponseEntity.ok(user);
    }


}
