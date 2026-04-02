package com.travora.backend.controller;

import com.travora.backend.model.Activity;
import com.travora.backend.model.Dining;
import com.travora.backend.service.GooglePlacesService;
import com.travora.backend.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@CrossOrigin(origins = "*")
public class PlaceController {

    private final RecommendationService recommendationService;
    private final GooglePlacesService googlePlacesService;

    public PlaceController(RecommendationService recommendationService,
                           GooglePlacesService googlePlacesService) {
        this.recommendationService = recommendationService;
        this.googlePlacesService = googlePlacesService;
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

    @PostMapping("/refresh")
    public ResponseEntity<String> manualRefresh() {
        googlePlacesService.refreshAllPlaces();
        return ResponseEntity.ok("Places refreshed successfully");
    }
}