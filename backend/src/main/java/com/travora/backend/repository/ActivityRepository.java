package com.travora.backend.repository;

import com.travora.backend.model.Activity;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends PlaceRepository<Activity> {
}