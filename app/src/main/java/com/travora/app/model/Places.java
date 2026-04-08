package com.travora.app.model;

import com.google.gson.annotations.SerializedName;

public class Places {

    @SerializedName("name")
    private String name;

    @SerializedName("rating")
    private Double rating;

    @SerializedName("address")
    private String address;

    @SerializedName("photoReference")
    private String photoReference;

    // Set programmatically based on which endpoint returned this place (not from JSON)
    private String type;

    public String getName() { return name; }
    public Double getRating() { return rating != null ? rating : 0.0; }
    public String getAddress() { return address; }
    public String getPhotoReference() { return photoReference; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
