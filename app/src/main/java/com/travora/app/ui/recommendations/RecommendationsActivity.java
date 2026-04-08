package com.travora.app.ui.recommendations;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.travora.app.R;
import com.travora.app.viewmodel.RecommendationsViewModel;

public class RecommendationsActivity extends AppCompatActivity {

    private static final String TAG = "RecommendationsActivity";

    private Button diningButton;
    private Button activitiesButton;
    private Button searchButton;
    private EditText searchBar;
    private RecyclerView recyclerView;
    private RecommendationsAdapter adapter;
    private RecommendationsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout_recommendations);

        diningButton = findViewById(R.id.dining_button);
        activitiesButton = findViewById(R.id.activities_button);
        searchButton = findViewById(R.id.search_button);
        searchBar = findViewById(R.id.search_bar);
        recyclerView = findViewById(R.id.recycler_view);

        adapter = new RecommendationsAdapter(new java.util.ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(RecommendationsViewModel.class);

        // Show all places (dining + activities merged) on initial load
        viewModel.getAllPlaces().observe(this, places -> {
            if (places == null) {
                Log.w(TAG, "getAllPlaces returned null");
                Toast.makeText(this, "Failed to load places", Toast.LENGTH_SHORT).show();
            } else if (places.isEmpty()) {
                Log.w(TAG, "getAllPlaces returned empty list - possible network error");
                Toast.makeText(this, "No places found. Check your connection.", Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "All places updated - showing " + places.size() + " places in featured view");
                adapter.updateList(places);
            }
        });

        diningButton.setOnClickListener(view -> {
            Log.d(TAG, "Dining button clicked");
            java.util.List<com.travora.app.model.Places> dining = viewModel.getDiningList().getValue();
            if (dining != null) {
                Log.d(TAG, "Showing " + dining.size() + " dining places");
                adapter.updateList(dining);
            } else {
                Log.w(TAG, "Dining list not loaded yet");
                Toast.makeText(this, "Still loading, please wait...", Toast.LENGTH_SHORT).show();
            }
        });

        activitiesButton.setOnClickListener(view -> {
            Log.d(TAG, "Activities button clicked");
            java.util.List<com.travora.app.model.Places> activities = viewModel.getActivitiesList().getValue();
            if (activities != null) {
                Log.d(TAG, "Showing " + activities.size() + " activity places");
                adapter.updateList(activities);
            } else {
                Log.w(TAG, "Activities list not loaded yet");
                Toast.makeText(this, "Still loading, please wait...", Toast.LENGTH_SHORT).show();
            }
        });

        searchButton.setOnClickListener(view ->
                Toast.makeText(this, "Search coming soon", Toast.LENGTH_SHORT).show()
        );

        Log.d(TAG, "onCreate() - triggering loadPlaces()");
        viewModel.loadPlaces();
    }
}
