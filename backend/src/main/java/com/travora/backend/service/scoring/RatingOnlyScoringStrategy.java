package com.travora.backend.service.scoring;

import com.travora.backend.model.Place;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Primary
public class RatingOnlyScoringStrategy implements ScoringStrategy {

    @Override
    public <T extends Place> List<T> rank(List<T> places) {
        return places.stream()
                .sorted(Comparator.comparingDouble(
                        (T p) -> p.getRating() != null ? -p.getRating() : 0.0
                ))
                .collect(Collectors.toList());
    }
}