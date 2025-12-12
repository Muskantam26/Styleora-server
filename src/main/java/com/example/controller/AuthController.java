package com.example.controller;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.security.JwtUtil;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")

public class AuthController {

    @Autowired private UserRepository repo;
    @Autowired private PasswordEncoder encoder;
    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setEmail(user.getEmail());
        user.setRole("ROLE_USER");
        repo.save(user);
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
      
    	 authManager.authenticate(
 	            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
 	        );

       
        User dbUser = repo.findByUsername(user.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 3. Generate JWT token
        String token = jwtUtil.generateToken(dbUser.getUsername());

        // 4. Return token, username, and role
        Map<String, Object> response = new HashMap<>();
        response.put("token",token);
        response.put("username", dbUser.getUsername());
        response.put("role", dbUser.getRole());

        return ResponseEntity.ok(response);
    }
}

