package com.travora.app.ui.recommendations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.travora.app.R;

import java.util.List;

public class RecommendationsAdapter extends RecyclerView.Adapter<RecommendationsAdapter.ViewHolder>{
    private List<Places> placesList;

    public RecommendationsAdapter(List<Places> list){
        this.placesList = list;
    }
    public void updateList(List<Places> newlist){
        placesList.clear();
        placesList.addAll(newlist);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate a single card layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_layout_recommendations_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Places place = placesList.get(position);
        holder.title.setText(place.getName());
        holder.description.setText(place.getDescription());
        holder.ratingBar.setRating(place.getRating());
        holder.image.setImageResource(place.getImageResId());
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
