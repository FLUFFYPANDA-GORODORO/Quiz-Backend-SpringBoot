package com.example.goro.controller;

import com.example.goro.entiry.AuthRequest;
import com.example.goro.entiry.AuthResponse;
import com.example.goro.entiry.User;
import com.example.goro.service.JwtService;
import com.example.goro.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private MyUserDetailsService userDetailsService;
    @Autowired private JwtService jwtService;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Default role if not provided
        if (user.getRole() == null) {
            user.setRole(com.example.goro.entiry.Role.STUDENT);
        }

        userDetailsService.saveUser(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            if (authentication.isAuthenticated()) {
                User loggedInUser = userDetailsService.getUserByUsername(request.getUsername());

                // ✅ Generate token with username + role
                String token = jwtService.generateToken(
                        loggedInUser.getUsername(),
                        loggedInUser.getRole().name()
                );

                // ✅ Return token, role, and username in response
                return ResponseEntity.ok(new AuthResponse(token, loggedInUser.getRole().name(), loggedInUser.getUsername()));
            } else {
                return ResponseEntity.status(401).body("Authentication failed");
            }
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
