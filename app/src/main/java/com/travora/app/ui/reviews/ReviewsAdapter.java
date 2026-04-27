package com.travora.app.ui.reviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.travora.app.R;
import com.travora.app.model.Reviews;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private List<Reviews> reviewList;

    public ReviewsAdapter(List<Reviews> reviews) {
        this.reviewList = reviews;
    }
    public void updateList(List<Reviews> newList) {
        reviewList.clear();
        reviewList.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_layout_review_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ViewHolder holder, int position) {
        Reviews review = reviewList.get(position);
        holder.username.setText(review.getUsername());
        holder.ratingBar.setRating(review.getUserRating());
        holder.reviewText.setText(review.getReview());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RatingBar ratingBar;
        TextView reviewText;
        TextView username;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.item_username);
            ratingBar = itemView.findViewById(R.id.item_user_ratingBar);
            reviewText = itemView.findViewById(R.id.item_user_review);
        }
    }
}
