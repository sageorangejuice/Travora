package com.travora.backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import com.travora.backend.model.LoginRequest;
import com.travora.backend.model.LoginResponse;
import com.travora.backend.model.RegistrationRequest;
import com.travora.backend.model.RegistrationResponse;
import com.travora.backend.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest payload) {
        return authService.login(payload);
    }

    @PostMapping("/register")
    public RegistrationResponse register(@RequestBody RegistrationRequest payload) {
        return authService.register(payload);
    }
}