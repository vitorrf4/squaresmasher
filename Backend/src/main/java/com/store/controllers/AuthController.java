package com.store.controllers;

import com.store.dto.NewUserDTO;
import com.store.models.AuthenticatedUser;
import com.store.models.AuthenticationRequest;
import com.store.models.AuthenticationResponse;
import com.store.models.User;
import com.store.services.AuthService;
import com.store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
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
        if (authenticationResponse == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        if (userService.isUserInvalid(user))
            return ResponseEntity.badRequest().build();

        if (!authService.isLoginCorrect(user))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        AuthenticatedUser authenticatedUser = authService.createAuthenticatedUser(user);

        return ResponseEntity.ok(authenticatedUser);
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

        return ResponseEntity.ok(newUser);
    }
}
