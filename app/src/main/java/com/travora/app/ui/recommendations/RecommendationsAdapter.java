package com.travora.app.ui.recommendations;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.travora.app.R;
import com.travora.app.model.Places;
import com.travora.app.network.RetrofitClient;
import com.travora.app.ui.reviews.ReviewsActivity;

import java.util.List;

public class RecommendationsAdapter extends RecyclerView.Adapter<RecommendationsAdapter.ViewHolder> {

    private List<Places> placesList;

    public RecommendationsAdapter(List<Places> list) {
        this.placesList = list;
    }

    public void updateList(List<Places> newList) {
        placesList.clear();
        placesList.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_layout_recommendations_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Places place = placesList.get(position);
        holder.title.setText(place.getName());
        String desc = (place.getDescription() != null && !place.getDescription().isEmpty())
                ? place.getDescription()
                : (place.getAddress() != null ? place.getAddress() : "");
        holder.description.setText(desc);
        holder.ratingBar.setRating(place.getRating().floatValue());
        String photoRef = place.getPhotoReference();
        Button reviewsButton = holder.itemView.findViewById(R.id.item_reviews);

        reviewsButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ReviewsActivity.class);
            intent.putExtra("place", place);
            view.getContext().startActivity(intent);
        });

        if (photoRef != null && !photoRef.isEmpty()) {
            String photoUrl = RetrofitClient.BASE_URL + "api/places/photo?reference=" + photoRef + "&maxwidth=400";
            Glide.with(holder.image.getContext())
                    .load(photoUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.placeholder_image);
        }
    }

    @Override
    public int getItemCount() {
        return placesList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, description;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_image);
            title = itemView.findViewById(R.id.item_title);
            description = itemView.findViewById(R.id.item_desc);
            ratingBar = itemView.findViewById(R.id.item_ratingBar);
        }
    }
}
