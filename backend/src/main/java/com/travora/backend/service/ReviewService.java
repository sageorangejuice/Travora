package com.travora.backend.service;

import com.travora.backend.model.Review;
import com.travora.backend.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getReviewsForPlace(String placeId) {
        return reviewRepository.findByPlaceId(placeId);
    }
}
