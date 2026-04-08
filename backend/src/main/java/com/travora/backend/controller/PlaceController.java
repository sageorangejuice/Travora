package com.travora.backend.controller;

import com.travora.backend.model.Activity;
import com.travora.backend.model.Dining;
import com.travora.backend.repository.ActivityRepository;
import com.travora.backend.repository.DiningRepository;
import com.travora.backend.service.GooglePlacesService;
import com.travora.backend.service.RecommendationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/places")
@CrossOrigin(origins = "*")
public class PlaceController {

    private final RecommendationService recommendationService;
    private final GooglePlacesService googlePlacesService;
    private final DiningRepository diningRepository;
    private final ActivityRepository activityRepository;

    public PlaceController(RecommendationService recommendationService,
                           GooglePlacesService googlePlacesService,
                           DiningRepository diningRepository,
                           ActivityRepository activityRepository) {
        this.recommendationService = recommendationService;
        this.googlePlacesService = googlePlacesService;
        this.diningRepository = diningRepository;
        this.activityRepository = activityRepository;
    }

    @GetMapping("/dining")
    public ResponseEntity<List<Dining>> getDining() {
        return ResponseEntity.ok(recommendationService.getTopDining());
    }

    @GetMapping("/activities")
    public ResponseEntity<List<Activity>> getActivities() {
        return ResponseEntity.ok(recommendationService.getTopActivities());
    }

    @GetMapping("/search")
    public ResponseEntity<RecommendationService.SearchResult> search(@RequestParam String query) {
        return ResponseEntity.ok(recommendationService.search(query));
    }

    @GetMapping("/photo")
    public ResponseEntity<byte[]> getPhoto(@RequestParam String reference) {
        Optional<Dining> dining = diningRepository.findByPhotoReference(reference);
        if (dining.isPresent() && dining.get().getPhotoData() != null) {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(dining.get().getPhotoData());
        }
        Optional<Activity> activity = activityRepository.findByPhotoReference(reference);
        if (activity.isPresent() && activity.get().getPhotoData() != null) {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(activity.get().getPhotoData());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> manualRefresh() {
        googlePlacesService.refreshAllPlaces();
        return ResponseEntity.ok("Places refreshed successfully");
    }
}