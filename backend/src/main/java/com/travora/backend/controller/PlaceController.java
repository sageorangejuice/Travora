package com.travora.backend.controller;

import com.travora.backend.model.Activity;
import com.travora.backend.model.Dining;
import com.travora.backend.service.GooglePlacesService;
import com.travora.backend.service.PlaceService;
import com.travora.backend.service.RecommendationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@CrossOrigin(origins = "*")
public class PlaceController {

    private final RecommendationService recommendationService;
    private final GooglePlacesService googlePlacesService;
    private final PlaceService placeService;

    public PlaceController(RecommendationService recommendationService,
                           GooglePlacesService googlePlacesService,
                           PlaceService placeService) {
        this.recommendationService = recommendationService;
        this.googlePlacesService = googlePlacesService;
        this.placeService = placeService;
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
        return placeService.getPhotoByReference(reference)
                .map(photo -> ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(photo))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> manualRefresh() {
        googlePlacesService.refreshAllPlaces();
        return ResponseEntity.ok("Places refreshed successfully");
    }
}
