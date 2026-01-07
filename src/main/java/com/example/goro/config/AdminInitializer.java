package com.example.goro.config;

import com.example.goro.dao.UserDao;
import com.example.goro.entiry.Role;
import com.example.goro.entiry.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String adminUsername = "admin1";

        // ✅ Check if admin already exists
        if (userDao.findByUsername(adminUsername).isPresent()) {
            System.out.println("✅ Admin already exists. Skipping creation.");
            return;
        }

        // ✅ Create admin user
        User admin = new User();
        admin.setUsername(adminUsername);
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(Role.ADMIN);

        userDao.save(admin);

        System.out.println("✅ Admin user created successfully.");
    }
}
