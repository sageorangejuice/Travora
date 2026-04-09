package com.travora.backend.controller;

import com.travora.backend.model.Review;
import com.travora.backend.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getReviews(@RequestParam String placeId) {
        return ResponseEntity.ok(reviewService.getReviewsForPlace(placeId));
    }
}
