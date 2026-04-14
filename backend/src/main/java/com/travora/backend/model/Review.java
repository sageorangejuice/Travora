package com.travora.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // References place_id in either the dining or activities table
    @Column(name = "place_id", nullable = false)
    private String placeId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "review", length = 2000, nullable = false)
    private String review;

    @Column(name = "user_rating", nullable = false)
    private Float userRating;

    // "Local" or "Tourist"
    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPlaceId() { return placeId; }
    public void setPlaceId(String placeId) { this.placeId = placeId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }

    public Float getUserRating() { return userRating; }
    public void setUserRating(Float userRating) { this.userRating = userRating; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
