package com.example.goro.controller;

import com.example.goro.entiry.User;
import com.example.goro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "https://quiz-backend.onrender.com")
public class UserController {

    @Autowired private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody User user) {
        Optional<User> existing = userService.getUserById(id);
        if (existing.isEmpty()) return ResponseEntity.status(404).body("User not found");
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }
}
