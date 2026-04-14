package com.travora.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.OffsetDateTime;

@Entity
@Table(name = "Travora-Users")
public class User {

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "pref_budget")
    private String prefBudget;

    @Column(name = "pref_diet")
    private String prefDiet;

    @Column(name = "pref_activity")
    private String prefActivity;

    @Column(name = "pref_dining")
    private String prefDining;

    public String getUsername() { return username; }
    public String getEmail() { return email; }

    public boolean verifyPassword(String rawPassword) {
        return PASSWORD_ENCODER.matches(rawPassword, this.password);
    }

    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPrefBudget() { return prefBudget; }
    public void setPrefBudget(String prefBudget) { this.prefBudget = prefBudget; }

    public String getPrefDiet() { return prefDiet; }
    public void setPrefDiet(String prefDiet) { this.prefDiet = prefDiet; }

    public String getPrefActivity() { return prefActivity; }
    public void setPrefActivity(String prefActivity) { this.prefActivity = prefActivity; }

    public String getPrefDining() { return prefDining; }
    public void setPrefDining(String prefDining) { this.prefDining = prefDining; }
}
