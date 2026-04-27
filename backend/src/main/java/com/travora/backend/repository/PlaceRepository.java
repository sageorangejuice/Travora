package com.travora.backend.repository;

import com.travora.backend.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface PlaceRepository<T extends Place> extends JpaRepository<T, Long> {

    List<T> findTop20ByOrderByRatingDesc();
    

    List<T> findByNameContainingIgnoreCase(String keyword);


    Optional<T> findByPlaceId(String placeId);


    Optional<T> findByPhotoReference(String photoReference);
}