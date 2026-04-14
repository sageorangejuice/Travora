package com.travora.backend.service;

import com.travora.backend.model.Activity;
import com.travora.backend.model.Dining;
import com.travora.backend.model.User;
import com.travora.backend.repository.ActivityRepository;
import com.travora.backend.repository.DiningRepository;
import com.travora.backend.repository.UserRepository;
import com.travora.backend.service.scoring.ScoringStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {

    private final DiningRepository diningRepository;
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final ScoringStrategy scoringStrategy;

    public RecommendationService(
            DiningRepository diningRepository,
            ActivityRepository activityRepository,
            UserRepository userRepository,
            ScoringStrategy scoringStrategy) {
        this.diningRepository = diningRepository;
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
        this.scoringStrategy = scoringStrategy;
    }

    public List<Dining> getTopDining(String username) {
        User user = username != null ? userRepository.findByUsername(username) : null;
        return scoringStrategy.rank(diningRepository.findTop20ByOrderByRatingDesc(), user);
    }

    public List<Activity> getTopActivities(String username) {
        User user = username != null ? userRepository.findByUsername(username) : null;
        return scoringStrategy.rank(activityRepository.findTop20ByOrderByRatingDesc(), user);
    }

    public SearchResult search(String keyword) {
        List<Dining> diningResults = diningRepository.findByNameContainingIgnoreCase(keyword);
        List<Activity> activityResults = activityRepository.findByNameContainingIgnoreCase(keyword);
        return new SearchResult(diningResults, activityResults);
    }

    public static class SearchResult {
        private final List<Dining> dining;
        private final List<Activity> activities;

        public SearchResult(List<Dining> dining, List<Activity> activities) {
            this.dining = dining;
            this.activities = activities;
        }

        public List<Dining> getDining() { return dining; }
        public List<Activity> getActivities() { return activities; }
    }
}