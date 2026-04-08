package com.travora.app.ui.reviews;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.travora.app.R;
import com.travora.app.model.Places;
import com.travora.app.model.Reviews;
import com.travora.app.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

public class ReviewsActivity extends AppCompatActivity {

    private ReviewsAdapter adapter;
    private List<Reviews> allReviews;
    String photoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout_reviews);

        Places place = (Places) getIntent().getSerializableExtra("place");

        TextView title = findViewById(R.id.place_title);
        ImageView image = findViewById(R.id.place_image);
        TextView description = findViewById(R.id.place_desc);
        RatingBar ratingBar = findViewById(R.id.place_ratingBar);

        title.setText(place.getName());
        description.setText(place.getDescription());
        photoRef = place.getPhotoReference();
        ratingBar.setRating(place.getRating().floatValue());

        if (photoRef != null && !photoRef.isEmpty()) {
            String photoUrl = RetrofitClient.BASE_URL + "api/places/photo?reference=" + photoRef + "&maxwidth=400";
            Glide.with(image.getContext())
                    .load(photoUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .into(image);
        } else {
            image.setImageResource(R.drawable.placeholder_image);
        }



        // Calculate average rating
        //float avgRating = 0;
       // allReviews = place.getReviews(); // save all reviews for filtering
      //  for (Reviews r : allReviews) {
        //    avgRating += r.getUserRating();
       // }
       // avgRating = allReviews.size() > 0 ? avgRating / allReviews.size() : 0;
      //  avgRatingBar.setRating(avgRating);

        allReviews = place.getSampleReviews();
        RecyclerView reviewsRecycler = findViewById(R.id.reviews_recycler_view);
        adapter = new ReviewsAdapter(allReviews);
        reviewsRecycler.setLayoutManager(new LinearLayoutManager(this));
        reviewsRecycler.setAdapter(adapter);

        Button buttonLocal = findViewById(R.id.local);
        Button buttonTourist = findViewById(R.id.tourist);

        buttonLocal.setOnClickListener(v -> filterReviewsByType("Local"));
        buttonTourist.setOnClickListener(v -> filterReviewsByType("Tourist"));
    }


    private void filterReviewsByType(String type) {
        List<Reviews> filtered = new ArrayList<>();
        for (Reviews r : allReviews) {
            if (r.getType().equalsIgnoreCase(type)) {
                filtered.add(r);
            }
        }
        adapter.updateList(filtered);
    }
}