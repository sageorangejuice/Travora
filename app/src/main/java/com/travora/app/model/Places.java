package com.travora.app.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class Places implements Serializable {

    @SerializedName("placeId")
    private String placeId;

    @SerializedName("name")
    private String name;

    @SerializedName("rating")
    private Double rating;

    @SerializedName("address")
    private String address;

    @SerializedName("photoReference")
    private String photoReference;

    @SerializedName("description")
    private String description;

    // Set programmatically based on which endpoint returned this place
    private String type;

    private List<Reviews> reviews;

    public String getPlaceId() { return placeId; }
    public String getName() { return name; }
    public Double getRating() { return rating != null ? rating : 0.0; }
    public String getAddress() { return address; }
    public String getPhotoReference() { return photoReference; }
    public String getDescription() { return description; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public List<Reviews> getReviews() { return reviews; }
    public void setReviews(List<Reviews> reviews) { this.reviews = reviews; }
}
