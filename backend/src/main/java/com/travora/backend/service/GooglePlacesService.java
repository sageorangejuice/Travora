package com.travora.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travora.backend.model.Activity;
import com.travora.backend.model.Dining;
import com.travora.backend.model.Place;
import com.travora.backend.repository.ActivityRepository;
import com.travora.backend.repository.DiningRepository;
import com.travora.backend.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Service
public class GooglePlacesService {

    private final DiningRepository diningRepository;
    private final ActivityRepository activityRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String apiKey;
    private final String location;
    private final String radius;

    public GooglePlacesService(
            DiningRepository diningRepository,
            ActivityRepository activityRepository,
            @Value("${google.places.api.key}") String apiKey,
            @Value("${google.places.location}") String location,
            @Value("${google.places.radius}") String radius) {
        this.diningRepository = diningRepository;
        this.activityRepository = activityRepository;
        this.apiKey = apiKey;
        this.location = location;
        this.radius = radius;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void refreshAllPlaces() {
        System.out.println("Starting daily Google Places refresh...");
        fetchAndSave("restaurant", diningRepository, Dining::new);
        fetchAndSave("tourist_attraction", activityRepository, Activity::new);
        System.out.println("Daily refresh complete.");
    }

    private <T extends Place> void fetchAndSave(
            String placeType,
            PlaceRepository<T> repository,
            Supplier<T> factory) {

        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json"
                + "?location=" + location
                + "&radius=" + radius
                + "&type=" + placeType
                + "&rankby=prominence"
                + "&key=" + apiKey;

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode results = objectMapper.readTree(response).get("results");

            List<T> places = new ArrayList<>();
            int count = 0;

            for (JsonNode node : results) {
                if (count >= 20) break;

                T place = factory.get();
                place.setPlaceId(node.get("place_id").asText());
                place.setName(node.get("name").asText());
                place.setAddress(node.path("vicinity").asText(null));
                place.setRating(node.path("rating").asDouble(0.0));
                String photoRef = node.path("photos").path(0).path("photo_reference").asText(null);
                place.setPhotoReference(photoRef);
                if (photoRef != null) {
                    String photoUrl = "https://maps.googleapis.com/maps/api/place/photo"
                            + "?maxwidth=400&photo_reference=" + photoRef + "&key=" + apiKey;
                    try {
                        byte[] imageBytes = restTemplate.getForObject(photoUrl, byte[].class);
                        place.setPhotoData(imageBytes);
                    } catch (Exception e) {
                        System.err.println("Failed to download photo for " + place.getName() + ": " + e.getMessage());
                    }
                }
                place.setLastUpdated(LocalDateTime.now());
                places.add(place);
                count++;
            }

            for (T p : places) {
                repository.findByPlaceId(p.getPlaceId())
                        .ifPresentOrElse(
                                existing -> {
                                    existing.setRating(p.getRating());
                                    existing.setLastUpdated(p.getLastUpdated());
                                    if (p.getPhotoData() != null) {
                                        existing.setPhotoData(p.getPhotoData());
                                    }
                                    repository.save(existing); // UPDATE
                                },
                                () -> repository.save(p)       // INSERT
                        );
            }

        } catch (Exception e) {
            System.err.println("Error fetching [" + placeType + "] places: " + e.getMessage());
        }
    }
}