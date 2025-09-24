package com.example.goro.service;

import com.example.goro.dao.UserDao;
import com.example.goro.entiry.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register a new user
    public ResponseEntity<String> registerUser(User user) {
        if (userDao.findByUsername(user.getUsername()).isPresent()) {
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    // Get all users
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userDao.findAll(), HttpStatus.OK);
    }

    // Get user by ID
    public Optional<User> getUserById(Integer id) {
        return userDao.findById(id);
    }

    // Update existing user
    public ResponseEntity<String> updateUser(Integer id, User updatedUser) {
        Optional<User> existingOpt = userDao.findById(id);
        if (existingOpt.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User user = existingOpt.get();
        user.setUsername(updatedUser.getUsername());

        // Only update password if provided
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        // Update role if provided
        if (updatedUser.getRole() != null) {
            user.setRole(updatedUser.getRole());
        }

        userDao.save(user);
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }

    // Delete user by ID
    public ResponseEntity<String> deleteUser(Integer id) {
        if (!userDao.existsById(id)) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        userDao.deleteById(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }
}
