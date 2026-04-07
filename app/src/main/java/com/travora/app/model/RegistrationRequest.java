package com.travora.app.model;

public class RegistrationRequest {
    private String fullName;
    private String username;
    private String phoneNumber;
    private String password;

    public RegistrationRequest(String fullName, String username, String phoneNumber, String password) {
        this.fullName = fullName;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getFullName() { return fullName; }
    public String getUsername() { return username; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getPassword() { return password; }
}
