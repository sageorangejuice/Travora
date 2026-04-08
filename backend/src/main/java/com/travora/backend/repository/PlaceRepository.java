package com.travora.backend.repository;

import com.travora.backend.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface PlaceRepository<T extends Place> extends JpaRepository<T, Long> {
    // gets top 20
    List<T> findTop20ByOrderByRatingDesc();
    
    // for searching using searchbar
    List<T> findByNameContainingIgnoreCase(String keyword);

    // gets details after user clicks
    Optional<T> findByPlaceId(String placeId);

    // used to serve stored photo bytes
    Optional<T> findByPhotoReference(String photoReference);
}