package com.travora.app.model;


import java.io.Serializable;

public class Reviews implements Serializable {
    private String type;
    private String username;
    private String review;
    private float userRating;

    public Reviews(String type, String username, String review, float userRating) {
        this.type = type;
        this.username = username;
        this.review = review;
        this.userRating = userRating;
    }

    public String getType(){
        return type;
    }
    public String getUsername(){
        return username;
    }
    public String getReview(){
        return review;
    }
    public float getUserRating(){
        return userRating;
    }

}
