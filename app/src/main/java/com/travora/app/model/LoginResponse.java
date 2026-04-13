package com.travora.app.model;

public class LoginResponse {
    private boolean success;
    private String username;

    public LoginResponse(boolean success, String username) {
        this.success = success;
        this.username = username;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getUsername() {
        return username;
    }
}
