package com.travora.app.model;

public class ProfilingRequest {
    private String username;
    private String budget;
    private String diet;
    private String activity;
    private String dining;

    public ProfilingRequest(String username, String budget, String diet, String activity, String dining) {
        this.username = username;
        this.budget   = budget;
        this.diet     = diet;
        this.activity = activity;
        this.dining   = dining;
    }

    public String getUsername() { return username; }
    public String getBudget()   { return budget; }
    public String getDiet()     { return diet; }
    public String getActivity() { return activity; }
    public String getDining()   { return dining; }
}
