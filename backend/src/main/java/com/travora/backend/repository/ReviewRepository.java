package com.travora.backend.repository;

import com.travora.backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByPlaceId(String placeId);
    java.util.Optional<Review> findByPlaceIdAndUsername(String placeId, String username);
}
