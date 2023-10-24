package com.store.controllers;

import com.store.dto.NewUserDTO;
import com.store.models.User;
import com.store.security.AuthenticationRequest;
import com.store.security.AuthenticationResponse;
import com.store.services.AuthService;
import com.store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@CrossOrigin(origins = {"https://vitorrf4.github.io", "http://localhost:4200"})
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/authentication")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse authenticationResponse = authService.setJwtToken(authenticationRequest);
        if (authenticationResponse == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUpUser(@RequestBody NewUserDTO newUserDTO) {
        if (NewUserDTO.isDTOInvalid(newUserDTO))
            return ResponseEntity.badRequest().build();

        if (userService.isUsernameAlreadyTaken(newUserDTO.name()))
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        User newUser = NewUserDTO.toUser(newUserDTO);

        newUser = authService.encodePassword(newUser);
        userService.saveUser(newUser);

        return ResponseEntity.ok(newUserDTO);
    }
}
