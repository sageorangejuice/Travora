package com.travora.app.ui.reviews;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.lifecycle.ViewModelProvider;

import com.travora.app.R;
import com.travora.app.model.Places;
import com.travora.app.model.Reviews;
import com.travora.app.network.RetrofitClient;
import com.travora.app.ui.profile.ProfileActivity;
import com.travora.app.ui.recommendations.RecommendationsActivity;
import com.travora.app.viewmodel.ReviewsViewModel;


import java.util.ArrayList;
import java.util.List;

public class ReviewsActivity extends AppCompatActivity {

    private ReviewsAdapter adapter;
    private List<Reviews> allReviews;
    private Places place;
    private String photoRef;
    private BottomNavigationView navView;
    private ReviewsViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout_reviews);
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
                startActivity(new Intent(this, ProfileActivity.class));
                return true;

            } else if (id == R.id.nav_back) {
                finish(); // 🔙 goes back
                return true;
            }

            return false;
        });

        // ✅ GET PLACE
        place = (Places) getIntent().getSerializableExtra("place");

        TextView title = findViewById(R.id.place_title);
        ImageView image = findViewById(R.id.place_image);
        TextView description = findViewById(R.id.place_desc);
        RatingBar ratingBar = findViewById(R.id.place_ratingBar);

        title.setText(place.getName());
        description.setText(place.getDescription() != null ? place.getDescription() : "");
        photoRef = place.getPhotoReference();
        ratingBar.setRating(place.getRating().floatValue());

        // ✅ LOAD IMAGE
        if (photoRef != null && !photoRef.isEmpty()) {
            String photoUrl = RetrofitClient.BASE_URL + "api/places/photo?reference=" + photoRef + "&maxwidth=400";

            Glide.with(this)
                    .load(photoUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .into(image);
        } else {
            image.setImageResource(R.drawable.placeholder_image);
        }

        // ✅ SET REVIEWS
        allReviews = new ArrayList<>();
        RecyclerView reviewsRecycler = findViewById(R.id.reviews_recycler_view);
        adapter = new ReviewsAdapter(allReviews);
        reviewsRecycler.setLayoutManager(new LinearLayoutManager(this));
        reviewsRecycler.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ReviewsViewModel.class);
        viewModel.getReviews().observe(this, reviews -> {
            allReviews = reviews;
            adapter.updateList(reviews);
        });
        viewModel.loadReviews(place);

        // ✅ BUTTONS
        Button buttonLocal = findViewById(R.id.local);
        Button buttonTourist = findViewById(R.id.tourist);
        Button buttonAddReview = findViewById(R.id.add_review_button);

        buttonLocal.setOnClickListener(v -> viewModel.filterByType("Local"));
        buttonTourist.setOnClickListener(v -> viewModel.filterByType("Tourist"));

        // 🔥 IMPORTANT FIX
        buttonAddReview.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddReviewActivity.class);
            intent.putExtra("place", place);

            startActivityForResult(intent, 100); // ✅ MUST USE THIS
        });
    }

    // ✅ MOVED OUTSIDE onCreate
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            Reviews newReview = (Reviews) data.getSerializableExtra("new_review");
            if (newReview != null) {
                viewModel.addReview(newReview);
            }
        }
    }

}