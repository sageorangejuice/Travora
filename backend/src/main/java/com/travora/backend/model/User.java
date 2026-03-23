package com.travora.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "Travora-Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    public String getUsernameFromDatabase() { 
        return username; 
    }
    public String getPasswordFromDatabase() { 
        return password;
    }
}

//LIXIANG'S CODE --> TO USE SOON
// package com.travora.backend.model;

// import jakarta.persistence.*;

// @Entity
// @Table(name = "user_credentials")
// public class User {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(name = "username", nullable = false, unique = true)
//     private String username;

//     @Column(name = "password", nullable = false)
//     private String password;

// // getters n setters
//     public Long getId() { return id; }

//     public String getUsername() { return username; }
//     public void setUsername(String username) { this.username = username; }

//     public String getPassword() { return password; }
//     public void setPassword(String password) { this.password = password; }
// }