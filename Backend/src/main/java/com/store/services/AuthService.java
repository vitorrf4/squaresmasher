package com.store.services;

import com.store.dto.NewUserDTO;
import com.store.models.User;
import com.store.repos.UserRepository;
import com.store.security.AuthenticationRequest;
import com.store.security.AuthenticationResponse;
import com.store.security.JwtTokenService;
import com.store.security.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public AuthService(UserService userService, PasswordEncoder passwordEncoder, UserRepository userRepository, AuthenticationManager authenticationManager, JwtUserDetailsService jwtUserDetailsService, JwtTokenService jwtTokenService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenService = jwtTokenService;
    }

    public AuthenticationResponse setJwtToken(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getName(), authenticationRequest.getPassword()));
        } catch (AuthenticationException ex) {
            return null;
        }

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getName());
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAccessToken(jwtTokenService.generateToken(userDetails));

        return authenticationResponse;
    }

    public User createUser(NewUserDTO userDTO) {
        User user = NewUserDTO.toUser(userDTO);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createUser(user);

        return user;
    }

    public boolean isUsernameAlreadyTaken(String name) {
        return userRepository.findByName(name) != null;
    }
}
