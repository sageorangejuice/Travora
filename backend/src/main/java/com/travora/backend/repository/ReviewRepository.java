package com.travora.backend.repository;

import com.travora.backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByPlaceId(String placeId);
    Optional<Review> findByPlaceIdAndUsername(String placeId, String username);

    @Query("SELECT AVG(r.userRating) FROM Review r")
    Double findAverageUserRating();

    List<Review> findByPlaceIdIn(List<String> placeIds);
}
