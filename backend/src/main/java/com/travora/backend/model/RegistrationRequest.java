package com.travora.backend.model;

public class RegistrationRequest {
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private String prefBudget;
    private String prefDiet;
    private String prefActivity;
    private String prefDining;

    public RegistrationRequest() {}

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getPassword() { return password; }
    public String getPrefBudget() { return prefBudget; }
    public String getPrefDiet() { return prefDiet; }
    public String getPrefActivity() { return prefActivity; }
    public String getPrefDining() { return prefDining; }

    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setPassword(String password) { this.password = password; }
    public void setPrefBudget(String prefBudget) { this.prefBudget = prefBudget; }
    public void setPrefDiet(String prefDiet) { this.prefDiet = prefDiet; }
    public void setPrefActivity(String prefActivity) { this.prefActivity = prefActivity; }
    public void setPrefDining(String prefDining) { this.prefDining = prefDining; }
}
