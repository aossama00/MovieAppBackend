package com.Sumerge.MovieApp.auth.service;

import com.Sumerge.MovieApp.auth.model.AuthenticationRequest;
import com.Sumerge.MovieApp.auth.model.AuthenticationResponse;
import com.Sumerge.MovieApp.auth.model.RegisterRequest;
import com.Sumerge.MovieApp.entity.User;
import com.Sumerge.MovieApp.repository.UserRepository;
import com.Sumerge.MovieApp.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor

public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User
                .builder()
                .email(request.getEmail())
                .pass(passwordEncoder.encode(request.getPassword()))
                .build();

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public UserDetails validate(HashMap<String, String> headers) {
        String token = headers.get("authorization").substring(7);
        String email = jwtService.extractUserName(token);
        return repository.findByEmail(email).orElseThrow();
    }
}