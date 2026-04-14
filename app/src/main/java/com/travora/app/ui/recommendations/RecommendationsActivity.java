package com.travora.app.ui.recommendations;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.travora.app.R;
import com.travora.app.ui.authentication.LoginActivity;
import com.travora.app.viewmodel.RecommendationsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RecommendationsActivity extends AppCompatActivity {

    private static final String TAG = "RecommendationsActivity";

    private Button diningButton;
    private Button activitiesButton;
    private Button searchButton;
    private EditText searchBar;
    private RecyclerView recyclerView;
    private RecommendationsAdapter adapter;
    private RecommendationsViewModel viewModel;
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout_recommendations);

        // ✅ AGGRESSIVE FIX: Completely strip all tinting from the nav bar and its items
        navView = findViewById(R.id.nav_bar);
        if (navView != null) {
            navView.setItemIconTintList(null); // Remove global tint
            Menu menu = navView.getMenu();
            for (int i = 0; i < menu.size(); i++) {
                MenuItem item = menu.getItem(i);
                Drawable icon = item.getIcon();
                if (icon != null) {
                    // Force the drawable to ignore any system tinting
                    Drawable wrappedIcon = DrawableCompat.wrap(icon.mutate());
                    DrawableCompat.setTintList(wrappedIcon, null);
                    item.setIcon(wrappedIcon);
                }
            }
        }
        BottomNavigationView bottomNav = findViewById(R.id.nav_bar);

        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                startActivity(new Intent(this, RecommendationsActivity.class));
                return true;

            } else if (id == R.id.nav_profile) {
                Toast.makeText(RecommendationsActivity.this, "Feature Coming Soon", Toast.LENGTH_SHORT).show();
                return true;

            } else if (id == R.id.nav_back) {
                finish(); // 🔙 goes back
                return true;
            }

            return false;
        });

        diningButton = findViewById(R.id.dining_button);
        activitiesButton = findViewById(R.id.activities_button);
        searchButton = findViewById(R.id.search_button);
        searchBar = findViewById(R.id.search_bar);
        recyclerView = findViewById(R.id.recycler_view);

        adapter = new RecommendationsAdapter(new java.util.ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(RecommendationsViewModel.class);

        viewModel.getAllPlaces().observe(this, places -> {
            if (places != null && !places.isEmpty()) {
                adapter.updateList(places);
            }
        });

        diningButton.setOnClickListener(view -> {
            java.util.List<com.travora.app.model.Places> dining = viewModel.getDiningList().getValue();
            if (dining != null) adapter.updateList(dining);
        });

        activitiesButton.setOnClickListener(view -> {
            java.util.List<com.travora.app.model.Places> activities = viewModel.getActivitiesList().getValue();
            if (activities != null) adapter.updateList(activities);
        });

        searchButton.setOnClickListener(view ->
                Toast.makeText(this, "Search coming soon", Toast.LENGTH_SHORT).show()
        );

        viewModel.loadPlaces();

    }

}
