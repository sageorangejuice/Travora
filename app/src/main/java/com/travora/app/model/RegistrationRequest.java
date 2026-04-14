package com.travora.app.model;

public class RegistrationRequest {
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private String prefBudget;
    private String prefDiet;
    private String prefActivity;
    private String prefDining;

    public RegistrationRequest(String username, String email, String phoneNumber, String password,
                                String prefBudget, String prefDiet, String prefActivity, String prefDining) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.prefBudget = prefBudget;
        this.prefDiet = prefDiet;
        this.prefActivity = prefActivity;
        this.prefDining = prefDining;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getPassword() { return password; }
    public String getPrefBudget() { return prefBudget; }
    public String getPrefDiet() { return prefDiet; }
    public String getPrefActivity() { return prefActivity; }
    public String getPrefDining() { return prefDining; }
}
