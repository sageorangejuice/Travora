package com.travora.app.ui.recommendations;

public class Places {
    private int imageResId;
    private float rating;
    private String name;
    private String description;
    private String type;

    public Places(int imageResId, float rating, String name, String description, String type) {
        this.imageResId = imageResId;
        this.rating = rating;
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public int getImageResId() {
        return imageResId;
    }

    public float getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    public String getType(){
        return type;
    }
}
