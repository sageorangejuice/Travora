package com.travora.app.ui.reviews;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

import com.travora.app.R;
import com.travora.app.model.Places;
import com.travora.app.model.Reviews;
import com.travora.app.model.UserManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.travora.app.ui.recommendations.RecommendationsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class AddReviewActivity extends AppCompatActivity {

    private Places place;

    private EditText reviewInput;
    private RatingBar ratingBar;
    private Button localButton, touristButton, submitButton;

    private String selectedType = "Local";
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout_addreview);
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
                Toast.makeText(AddReviewActivity.this, "Feature Coming Soon", Toast.LENGTH_SHORT).show();
                return true;

            } else if (id == R.id.nav_back) {
                finish(); // 🔙 goes back
                return true;
            }

            return false;
        });

        // ===== GET PLACE =====
        place = (Places) getIntent().getSerializableExtra("place");

        // ===== INIT UI =====
        reviewInput = findViewById(R.id.review_input);
        ratingBar = findViewById(R.id.place_ratingBar);

        localButton = findViewById(R.id.local_button);
        touristButton = findViewById(R.id.Tourist_button);
        submitButton = findViewById(R.id.submit_review_button);

        // ===== BUTTON TOGGLE =====
        highlightSelected(localButton, touristButton);

        localButton.setOnClickListener(v -> {
            selectedType = "Local";
            highlightSelected(localButton, touristButton);
        });

        touristButton.setOnClickListener(v -> {
            selectedType = "Tourist";
            highlightSelected(touristButton, localButton);
        });

        // ===== SUBMIT =====
        submitButton.setOnClickListener(v -> {

            String text = reviewInput.getText().toString().trim();
            float rating = ratingBar.getRating();

            if (text.isEmpty()) {
                reviewInput.setError("Enter a review");
                return;
            }

            // 🔥 GET USERNAME
            String username = "Anonymous";
            if (UserManager.getUser() != null) {
                username = UserManager.getUser().getUsername();
            }

            Reviews newReview = new Reviews(
                    selectedType,
                    username,
                    text,
                    rating
            );

            // In a real app, you'd add this to the database. 
            // For now, we pass it back to the ReviewsActivity.
            Intent resultIntent = new Intent();
            resultIntent.putExtra("new_review", newReview);
            setResult(RESULT_OK, resultIntent);

            Toast.makeText(this, "Thanks for your review!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void highlightSelected(Button selected, Button other) {
        selected.setAlpha(1.0f);
        other.setAlpha(0.5f);
    }
}
