package com.travora.backend.model;

public class RegistrationRequest {
    private String fullName;
    private String username;
    private String phoneNumber;
    private String password;

    public RegistrationRequest() {}

    public String getFullName() { return fullName; }
    public String getUsername() { return username; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getPassword() { return password; }

    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setUsername(String username) { this.username = username; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setPassword(String password) { this.password = password; }
}
