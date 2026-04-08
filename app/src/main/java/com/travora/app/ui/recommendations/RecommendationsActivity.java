package com.travora.app.ui.recommendations;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.travora.app.R;
import com.travora.app.ui.authentication.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class RecommendationsActivity extends AppCompatActivity {
    private Button diningButton;
    private Button activitiesButton;
    private Button searchButton;
    private EditText searchBar;
    private RecyclerView recyclerView;
    private RecommendationsAdapter adapter;
    private List<Places> placesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout_recommendations);

        diningButton = findViewById(R.id.dining_button);
        activitiesButton = findViewById(R.id.activities_button);
        searchButton = findViewById(R.id.search_button);
        searchBar = findViewById(R.id.search_bar);
        recyclerView = findViewById(R.id.recycler_view);

        placesList = new ArrayList<>();
        // Note: Using sample_image if bedok_corner is missing to avoid other errors
        placesList.add(new Places(R.drawable.bedok_corner, 4.5f, "Bedok Corner", "Local food center", "Dining"));
        placesList.add(new Places(R.drawable.duck_tours, 4.8f, "Duck Tours", "Local tour", "Activities"));

        adapter = new RecommendationsAdapter(new ArrayList<>(placesList)); // initially show all places
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // vertical scrolling
        recyclerView.setAdapter(adapter);

        diningButton.setOnClickListener(view -> filterByType("Dining"));
        activitiesButton.setOnClickListener(view -> filterByType("Activities"));
    }

    private void filterByType(String type) {
        List<Places> filtered = new ArrayList<>();
        for (Places p : placesList) {
            if (p.getType() != null && p.getType().equals(type)) {
                filtered.add(p);
            }
        }

        adapter.updateList(filtered);
        searchButton.setOnClickListener(view ->
                Toast.makeText(RecommendationsActivity.this, "Feature Coming Soon", Toast.LENGTH_SHORT).show()
        );
    }

}
