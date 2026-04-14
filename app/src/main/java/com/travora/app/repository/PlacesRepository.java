package com.travora.app.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.travora.app.model.Places;
import com.travora.app.model.Reviews;
import com.travora.app.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesRepository {

    private static final String TAG = "PlacesRepository";

    public void fetchDining(MutableLiveData<List<Places>> diningResult) {
        Log.d(TAG, "fetchDining() - calling GET /api/places/dining");
        RetrofitClient.getApiService().getTopDining().enqueue(new Callback<List<Places>>() {
            @Override
            public void onResponse(Call<List<Places>> call, Response<List<Places>> response) {
                Log.d(TAG, "fetchDining onResponse - code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    List<Places> list = response.body();
                    for (Places p : list) p.setType("Dining");
                    Log.d(TAG, "fetchDining success - " + list.size() + " places returned");
                    diningResult.postValue(list);
                } else {
                    Log.w(TAG, "fetchDining failed - code: " + response.code());
                    diningResult.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Places>> call, Throwable t) {
                Log.e(TAG, "fetchDining onFailure - " + t.getMessage(), t);
                diningResult.postValue(null);
            }
        });
    }

    public void fetchActivities(MutableLiveData<List<Places>> activitiesResult) {
        Log.d(TAG, "fetchActivities() - calling GET /api/places/activities");
        RetrofitClient.getApiService().getTopActivities().enqueue(new Callback<List<Places>>() {
            @Override
            public void onResponse(Call<List<Places>> call, Response<List<Places>> response) {
                Log.d(TAG, "fetchActivities onResponse - code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    List<Places> list = response.body();
                    for (Places p : list) p.setType("Activities");
                    Log.d(TAG, "fetchActivities success - " + list.size() + " places returned");
                    activitiesResult.postValue(list);
                } else {
                    Log.w(TAG, "fetchActivities failed - code: " + response.code());
                    activitiesResult.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Places>> call, Throwable t) {
                Log.e(TAG, "fetchActivities onFailure - " + t.getMessage(), t);
                activitiesResult.postValue(null);
            }
        });
    }

    public void submitReview(Reviews review) {
        RetrofitClient.getApiService().postReview(review).enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                if (!response.isSuccessful()) {
                    Log.w(TAG, "submitReview failed - code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {
                Log.e(TAG, "submitReview onFailure - " + t.getMessage(), t);
            }
        });
    }

    public void fetchReviews(String placeId, MutableLiveData<List<Reviews>> result) {
        RetrofitClient.getApiService().getReviews(placeId).enqueue(new Callback<List<Reviews>>() {
            @Override
            public void onResponse(Call<List<Reviews>> call, Response<List<Reviews>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    result.postValue(response.body());
                } else {
                    result.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Reviews>> call, Throwable t) {
                Log.e(TAG, "fetchReviews onFailure - " + t.getMessage(), t);
                result.postValue(null);
            }
        });
    }
}
