package com.travora.backend.service;

import com.travora.backend.model.LoginRequest;
import com.travora.backend.model.LoginResponse;
import com.travora.backend.model.PreferencesRequest;
import com.travora.backend.model.RegistrationRequest;
import com.travora.backend.model.RegistrationResponse;
import com.travora.backend.model.User;
import com.travora.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponse login(LoginRequest payload) {
        logger.debug("[AUTH SERVICE] Login attempt for email: '{}'", payload.getUsername());

        User user = userRepository.findByEmail(payload.getUsername());

        if (user == null) {
            logger.debug("[AUTH SERVICE] No user found for email: '{}'", payload.getUsername());
            return new LoginResponse(false, null);
        }

        logger.debug("[AUTH SERVICE] User found. Verifying password...");
        boolean passwordMatch = user.verifyPassword(payload.getPassword());
        logger.debug("[AUTH SERVICE] Password match: {}", passwordMatch);
        return new LoginResponse(passwordMatch, passwordMatch ? user.getUsername() : null);
    }

    public boolean savePreferences(PreferencesRequest payload) {
        User user = userRepository.findByUsername(payload.getUsername());
        if (user == null) return false;
        user.setPrefBudget(payload.getBudget());
        user.setPrefDiet(payload.getDiet());
        user.setPrefActivity(payload.getActivity());
        user.setPrefDining(payload.getDining());
        userRepository.save(user);
        return true;
    }

    public RegistrationResponse register(RegistrationRequest payload) {
        logger.debug("[AUTH SERVICE] Register attempt for username: '{}'", payload.getUsername());

        if (userRepository.existsByEmail(payload.getEmail())) {
            logger.debug("[AUTH SERVICE] Email already exists: '{}'", payload.getEmail());
            return new RegistrationResponse(false, "An account with this email already exists");
        }

        try {
            User user = new User();
            user.setUsername(payload.getUsername());
            user.setEmail(payload.getEmail());
            user.setPassword(passwordEncoder.encode(payload.getPassword()));
            user.setCreatedAt(OffsetDateTime.now());
            user.setPhoneNumber(payload.getPhoneNumber());
            user.setPrefBudget(payload.getPrefBudget());
            user.setPrefDiet(payload.getPrefDiet());
            user.setPrefActivity(payload.getPrefActivity());
            user.setPrefDining(payload.getPrefDining());
            userRepository.save(user);
            logger.debug("[AUTH SERVICE] User saved successfully: '{}'", payload.getUsername());
            return new RegistrationResponse(true, "Registration successful");
        } catch (Exception e) {
            logger.error("[AUTH SERVICE] Failed to save user: {}", e.getMessage(), e);
            return new RegistrationResponse(false, "Registration failed: " + e.getMessage());
        }
    }
}
