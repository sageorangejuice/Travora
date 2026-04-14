package com.travora.app.model;

public class RegistrationResponse {
    private boolean success;
    private String message;

    public RegistrationResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}
