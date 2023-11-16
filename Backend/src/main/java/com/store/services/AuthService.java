package com.store.services;

import com.store.models.AuthenticatedUser;
import com.store.models.User;
import com.store.models.AuthenticationRequest;
import com.store.models.AuthenticationResponse;
import com.store.repos.UserRepository;
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
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                       JwtUserDetailsService jwtUserDetailsService, JwtTokenService jwtTokenService,
                       UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
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

    public boolean isLoginCorrect(User user) {
        User userOnDb = userRepository.findByName(user.getName());

        return passwordEncoder.matches(user.getPassword(), userOnDb.getPassword());
    }

    public AuthenticatedUser createAuthenticatedUser(User user) {
        AuthenticationResponse jwtToken = setJwtToken(new AuthenticationRequest(user.getName(), user.getPassword()));

        user = userRepository.findByName(user.getName());

        return new AuthenticatedUser(user.getId(), user.getName(), user.getStore().getName(), jwtToken.getAccessToken());
    }

    public User encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return user;
    }

}
