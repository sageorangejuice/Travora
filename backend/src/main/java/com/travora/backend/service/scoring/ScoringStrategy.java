package com.travora.backend.service.scoring;

import com.travora.backend.model.Place;

import java.util.List;

public interface ScoringStrategy {
    // Generic method: works for both List<Dining> and List<Activity>
    <T extends Place> List<T> rank(List<T> places);
}