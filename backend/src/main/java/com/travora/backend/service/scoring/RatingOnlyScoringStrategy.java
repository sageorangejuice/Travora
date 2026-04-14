package com.travora.backend.service.scoring;

import com.travora.backend.model.Place;
import com.travora.backend.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("ratingOnlyScoringStrategy")
public class RatingOnlyScoringStrategy implements ScoringStrategy {

    @Override
    public <T extends Place> List<T> rank(List<T> places, User user) {
        ArrayList<T> ranked = new ArrayList<>(places);
        ranked.sort((a, b) -> Double.compare(
                b.getRating() != null ? b.getRating() : 0.0,
                a.getRating() != null ? a.getRating() : 0.0
        ));
        return ranked;
    }
}
