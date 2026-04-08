package com.travora.app.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Places implements Serializable {

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


    // Set programmatically based on which endpoint returned this place (not from JSON)
    private String type;
    private List<Reviews> reviews;

    private static final List<Reviews> sampleReviews = new ArrayList<>();
    static {
        List<Reviews> list = new ArrayList<>();
        list.add(new Reviews("Local", "Babu Suresh33", "Amazing atmosphere!", 4.5f));
        list.add(new Reviews("Local", "Thamotharam S/O Muthu", "Good, but a bit crowded.", 3.5f));
        list.add(new Reviews("Tourist", "Itachi", "Loved it!", 5.0f));
        list.add(new Reviews("Tourist", "Luffy", "Great experience for visitors.", 4.0f));
    }

    public String getName() { return name; }
    public Double getRating() { return rating != null ? rating : 0.0; }
    public String getAddress() { return address; }
    public String getPhotoReference() { return photoReference; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDescription() { return description; }

    public List<Reviews> getReviews() { return reviews; }
    public List<Reviews> getSampleReviews() { return sampleReviews;}

}
