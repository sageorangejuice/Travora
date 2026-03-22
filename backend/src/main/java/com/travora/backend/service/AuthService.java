package com.travora.backend.service;

import com.travora.backend.model.LoginRequest;
import com.travora.backend.model.User;
import com.travora.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

// I will use hashing later on
@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean login(LoginRequest payload) {
        User user = userRepository.findByUsername(payload.getUsername());

        if (user == null) {
            return false;  
        }

        return user.getPasswordFromDatabase().equals(payload.getPassword());
       
    }
}
