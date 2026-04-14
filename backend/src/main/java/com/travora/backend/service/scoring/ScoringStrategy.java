package com.travora.backend.service.scoring;

import com.travora.backend.model.Place;
import com.travora.backend.model.User;

import java.util.List;

public interface ScoringStrategy {
    <T extends Place> List<T> rank(List<T> places, User user);
}