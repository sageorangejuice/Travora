package com.travora.app.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.travora.app.model.Places;
import com.travora.app.model.UserManager;
import com.travora.app.repository.PlacesRepository;

import java.util.ArrayList;
import java.util.List;

public class RecommendationsViewModel extends ViewModel {

    private static final String TAG = "RecommendationsViewModel";

    private final MutableLiveData<List<Places>> diningList = new MutableLiveData<>();
    private final MutableLiveData<List<Places>> activitiesList = new MutableLiveData<>();
    private final MutableLiveData<List<Places>> allPlaces = new MutableLiveData<>();

    private final PlacesRepository repository = new PlacesRepository();

    // Tracks how many of the two calls have completed so we know when to merge
    private List<Places> loadedDining = null;
    private List<Places> loadedActivities = null;
    private List<Places> allPlacesMaster = new ArrayList<>();

    public MutableLiveData<List<Places>> getDiningList() { return diningList; }
    public MutableLiveData<List<Places>> getActivitiesList() { return activitiesList; }
    public MutableLiveData<List<Places>> getAllPlaces() { return allPlaces; }

    public void loadPlaces() {
        Log.d(TAG, "loadPlaces() - fetching dining and activities from backend");
        loadedDining = null;
        loadedActivities = null;

        String username = UserManager.getUser() != null ? UserManager.getUser().getUsername() : null;

        repository.fetchDining(username, new MutableLiveData<List<Places>>() {
            @Override
            public void postValue(List<Places> value) {
                Log.d(TAG, "Dining data received - " + (value != null ? value.size() + " places" : "null"));
                loadedDining = value != null ? value : new ArrayList<>();
                diningList.postValue(loadedDining);
                tryMerge();
            }
        });

        repository.fetchActivities(username, new MutableLiveData<List<Places>>() {
            @Override
            public void postValue(List<Places> value) {
                Log.d(TAG, "Activities data received - " + (value != null ? value.size() + " places" : "null"));
                loadedActivities = value != null ? value : new ArrayList<>();
                activitiesList.postValue(loadedActivities);
                tryMerge();
            }
        });
    }

    public void search(String query) {
        if (allPlacesMaster.isEmpty()) return;
        if (query == null || query.trim().isEmpty()) {
            allPlaces.postValue(allPlacesMaster);
            return;
        }
        String lower = query.trim().toLowerCase();
        List<Places> filtered = new ArrayList<>();
        for (Places p : allPlacesMaster) {
            if (p.getName() != null && p.getName().toLowerCase().startsWith(lower)) {
                filtered.add(p);
            }
        }
        allPlaces.postValue(filtered);
    }

    private void tryMerge() {
        if (loadedDining != null && loadedActivities != null) {
            List<Places> merged = new ArrayList<>();
            merged.addAll(loadedDining);
            merged.addAll(loadedActivities);
            allPlacesMaster = merged;
            Log.d(TAG, "Both lists loaded - merging " + merged.size() + " total places for featured view");
            allPlaces.postValue(merged);
        }
    }
}
