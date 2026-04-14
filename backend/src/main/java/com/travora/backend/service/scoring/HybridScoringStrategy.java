package com.travora.backend.service.scoring;

import com.travora.backend.model.Place;
import com.travora.backend.model.Review;
import com.travora.backend.model.User;
import com.travora.backend.repository.ReviewRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Primary
public class HybridScoringStrategy implements ScoringStrategy {

    private static final int BAYESIAN_MIN_REVIEWS = 5;

    private final ReviewRepository reviewRepository;

    public HybridScoringStrategy(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public <T extends Place> List<T> rank(List<T> places, User user) {
        Set<String> userTags = buildUserTags(user);
        Double avg = reviewRepository.findAverageUserRating();
        double globalMean = (avg != null) ? avg : 3.5;

        List<String> placeIds = places.stream().map(Place::getPlaceId).collect(Collectors.toList());
        Map<String, List<Review>> reviewsByPlace = reviewRepository.findByPlaceIdIn(placeIds)
                .stream().collect(Collectors.groupingBy(Review::getPlaceId));

        ArrayList<T> ranked = new ArrayList<>(places);
        ranked.sort((a, b) -> Double.compare(
                score(b, userTags, globalMean, reviewsByPlace),
                score(a, userTags, globalMean, reviewsByPlace)
        ));
        return ranked;
    }

    private <T extends Place> double score(T place, Set<String> userTags, double globalMean,
                                            Map<String, List<Review>> reviewsByPlace) {
        double jaccard = jaccard(userTags, place.getTagSet());
        double bayesian = bayesian(place.getPlaceId(), globalMean, reviewsByPlace) / 5.0;
        return jaccard * bayesian;
    }

    private double jaccard(Set<String> userTags, Set<String> placeTags) {
        if (userTags.isEmpty()) return 1.0;
        if (placeTags.isEmpty()) return 0.5;

        Set<String> intersection = new HashSet<>(userTags);
        intersection.retainAll(placeTags);

        Set<String> union = new HashSet<>(userTags);
        union.addAll(placeTags);

        return (double) intersection.size() / union.size();
    }

    private double bayesian(String placeId, double globalMean, Map<String, List<Review>> reviewsByPlace) {
        List<Review> reviews = reviewsByPlace.getOrDefault(placeId, List.of());
        int n = reviews.size();
        if (n == 0) return globalMean;
        double sumRatings = reviews.stream()
                .mapToDouble(r -> r.getUserRating() != null ? r.getUserRating() : 0.0)
                .sum();
        return (globalMean * BAYESIAN_MIN_REVIEWS + sumRatings) / (BAYESIAN_MIN_REVIEWS + n);
    }

    private Set<String> buildUserTags(User user) {
        Set<String> tags = new HashSet<>();
        if (user == null) return tags;
        if (user.getPrefBudget() != null) tags.add(user.getPrefBudget().toLowerCase());
        if (user.getPrefActivity() != null) tags.add(user.getPrefActivity().toLowerCase());
        if (user.getPrefDining() != null) tags.add(user.getPrefDining().toLowerCase().replace(" ", "_"));
        if (user.getPrefDiet() != null && !user.getPrefDiet().equalsIgnoreCase("none")) {
            tags.add(user.getPrefDiet().toLowerCase());
        }
        return tags;
    }
}
