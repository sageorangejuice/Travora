package com.travora.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
public abstract class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "place_id", unique = true, nullable = false)
    private String placeId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "website")
    private String website;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "opening_hours", columnDefinition = "TEXT")
    private String openingHours;

    @Column(name = "photo_reference", columnDefinition = "TEXT")
    private String photoReference;

    @Column(name = "description", length = 2000)
    private String description;

    @JsonIgnore
    @Column(name = "photo_data", columnDefinition = "bytea")
    private byte[] photoData;

    @Column(name = "tags")
    private String tags;

    @Column(name = "price_level")
    private Integer priceLevel;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    // no setter for id
    public Long getId() { return id; }

    public String getPlaceId() { return placeId; }
    public void setPlaceId(String placeId) { this.placeId = placeId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getOpeningHours() { return openingHours; }
    public void setOpeningHours(String openingHours) { this.openingHours = openingHours; }

    public String getPhotoReference() { return photoReference; }
    public void setPhotoReference(String photoReference) { this.photoReference = photoReference; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public byte[] getPhotoData() { return photoData; }
    public void setPhotoData(byte[] photoData) { this.photoData = photoData; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public Integer getPriceLevel() { return priceLevel; }
    public void setPriceLevel(Integer priceLevel) { this.priceLevel = priceLevel; }

    public Set<String> getTagSet() {
        if (tags == null || tags.isBlank()) return Collections.emptySet();
        return new HashSet<>(Arrays.asList(tags.split(",")));
    }

    public void setTagSet(Set<String> tagSet) {
        this.tags = tagSet.isEmpty() ? null : String.join(",", tagSet);
    }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}