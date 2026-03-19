package com.travora.backend.service;

import com.travora.backend.model.User;
import com.travora.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    // REGISTER
    public String register(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            return "ERROR: Username already taken";
        } // check for dupes
        User user = new User();
        user.setUsername(username);
        userRepository.save(user); // insert into user_credentials
        return "Registered successfully";
    }

    // LOGIN
    public String login(String username, String password) {
        User user = userRepository.findByUsername(username) // searching for user
            .orElse(null);

        // check for match
        if (user == null) {
            return "ERROR: User not found";
        } 
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "ERROR: Invalid password";
        } 
        return "Login successful. Welcome, " + username;
    }
}
