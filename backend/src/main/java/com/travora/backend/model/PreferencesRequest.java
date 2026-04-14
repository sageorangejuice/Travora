package com.travora.backend.model;

public class PreferencesRequest {

    private String username;
    private String budget;
    private String diet;
    private String activity;
    private String dining;

    public String getUsername() { return username; }
    public String getBudget()   { return budget; }
    public String getDiet()     { return diet; }
    public String getActivity() { return activity; }
    public String getDining()   { return dining; }
}
