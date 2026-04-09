package com.travora.backend.service.scoring;

import com.travora.backend.model.Place;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class RatingOnlyScoringStrategy implements ScoringStrategy {

    @Override
    public <T extends Place> List<T> rank(List<T> places) {
        ArrayList<T> ranked = new ArrayList<>(places);
        ranked.sort((a, b) -> Double.compare(computeScore(b), computeScore(a)));
        return ranked;
    }

    private <T extends Place> double computeScore(T place) {
        return place.getRating() != null ? place.getRating() : 0.0;
    }
}
