package com.travora.app.ui.recommendations;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.travora.app.R;
import com.travora.app.model.Places;
import com.travora.app.model.UserManager;
import com.travora.app.ui.profile.ProfileActivity;
import com.travora.app.viewmodel.RecommendationsViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecommendationsActivity extends AppCompatActivity {

    private static final String TAG = "RecommendationsActivity";

    private Button diningButton;
    private Button activitiesButton;
    private Button searchButton;
    private EditText searchBar;
    private RecyclerView recyclerView;
    private RecommendationsAdapter adapter;
    private RecommendationsViewModel viewModel;
    private ProgressBar progressBar;
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout_recommendations);
        UserManager.loadFromPrefs(this);

        navView = findViewById(R.id.nav_bar);
        if (navView != null) {
            navView.setItemIconTintList(null);
            Menu menu = navView.getMenu();
            for (int i = 0; i < menu.size(); i++) {
                MenuItem item = menu.getItem(i);
                Drawable icon = item.getIcon();
                if (icon != null) {
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
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;

            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;

            } else if (id == R.id.nav_back) {
                finish();
                return true;
            }

            return false;
        });

        diningButton = findViewById(R.id.dining_button);
        activitiesButton = findViewById(R.id.activities_button);
        searchButton = findViewById(R.id.search_button);
        searchBar = findViewById(R.id.search_bar);
        recyclerView = findViewById(R.id.recycler_view);

        progressBar = findViewById(R.id.progress_bar);

        adapter = new RecommendationsAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(RecommendationsViewModel.class);

        viewModel.getAllPlaces().observe(this, places -> {
            progressBar.setVisibility(View.GONE);
            if (places != null && !places.isEmpty()) {
                adapter.updateList(places);
            }
        });

        diningButton.setOnClickListener(view -> {
            List<Places> dining = viewModel.getDiningList().getValue();
            if (dining != null) adapter.updateList(dining);
        });

        activitiesButton.setOnClickListener(view -> {
            List<Places> activities = viewModel.getActivitiesList().getValue();
            if (activities != null) adapter.updateList(activities);
        });

        searchButton.setOnClickListener(view ->
                Toast.makeText(this, "Search coming soon", Toast.LENGTH_SHORT).show()
        );

        viewModel.loadPlaces();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
