package com.travora.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.travora.app.model.Places;
import com.travora.app.model.Reviews;
import com.travora.app.repository.PlacesRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReviewsViewModel extends ViewModel {

    private static final List<Reviews> SAMPLE_REVIEWS = Arrays.asList(
            new Reviews("Local",   "Babu Suresh33",         "Amazing atmosphere!", 4.5f),
            new Reviews("Local",   "Thamotharam S/O Muthu", "Good, but a bit crowded.", 3.5f),
            new Reviews("Tourist", "Itachi",                "Loved it!", 5.0f),
            new Reviews("Tourist", "Luffy",                 "Great experience for visitors.", 4.0f)
    );

    private final PlacesRepository repository = new PlacesRepository();
    private final MutableLiveData<List<Reviews>> reviewsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Float> averageRating = new MutableLiveData<>();
    private List<Reviews> allReviews = new ArrayList<>();
    private MutableLiveData<List<Reviews>> fetchResult;
    private Observer<List<Reviews>> fetchObserver;

    public LiveData<List<Reviews>> getReviews() {
        return reviewsLiveData;
    }

    public LiveData<Float> getAverageRating() {
        return averageRating;
    }

    private float computeAverage(List<Reviews> reviews) {
        if (reviews == null || reviews.isEmpty()) return 0f;
        float sum = 0f;
        for (Reviews r : reviews) sum += r.getUserRating();
        return sum / reviews.size();
    }

    public void loadReviews(Places place) {
        String placeId = place.getPlaceId();
        if (placeId == null || placeId.isEmpty()) {
            allReviews = SAMPLE_REVIEWS;
            reviewsLiveData.setValue(allReviews);
            return;
        }

        if (fetchResult != null && fetchObserver != null) {
            fetchResult.removeObserver(fetchObserver);
        }
        fetchResult = new MutableLiveData<>();
        fetchObserver = reviews -> {
            if (reviews != null && !reviews.isEmpty()) {
                allReviews = reviews;
            } else {
                allReviews = SAMPLE_REVIEWS;
            }
            reviewsLiveData.setValue(allReviews);
        };
        fetchResult.observeForever(fetchObserver);
        repository.fetchReviews(placeId, fetchResult);
    }

    public void filterByType(String type) {
        List<Reviews> filtered = new ArrayList<>();
        for (Reviews r : allReviews) {
            if (type.equalsIgnoreCase(r.getType())) {
                filtered.add(r);
            }
        }
        reviewsLiveData.setValue(filtered);
        averageRating.setValue(computeAverage(filtered));
    }

    public void showAll() {
        reviewsLiveData.setValue(allReviews);
        averageRating.setValue(computeAverage(allReviews));
    }

    public void addReview(Reviews review) {
        List<Reviews> updated = new ArrayList<>(allReviews);
        updated.add(review);
        allReviews = updated;
        reviewsLiveData.setValue(allReviews);
        averageRating.setValue(computeAverage(allReviews));
    }

    public void submitReview(Reviews review) {
        repository.submitReview(review);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (fetchResult != null && fetchObserver != null) {
            fetchResult.removeObserver(fetchObserver);
        }
    }
}
