package com.example.goro.config;

import com.example.goro.entiry.Role;
import com.example.goro.entiry.User;
import com.example.goro.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String adminUsername = "admin1";

        // ✅ If admin already exists, do nothing
        if (userRepository.findByUsername(adminUsername).isPresent()) {
            System.out.println("✅ Admin already exists. Skipping creation.");
            return;
        }

        User admin = new User();
        admin.setUsername(adminUsername);
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(Role.ADMIN);

        userRepository.save(admin);

        System.out.println("✅ Admin user created successfully.");
    }
}
