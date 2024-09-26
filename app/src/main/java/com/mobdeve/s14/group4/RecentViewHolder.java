package com.mobdeve.s14.group4;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class RecentViewHolder extends RecyclerView.ViewHolder  {
    public ImageView ivRecentPic;
    private TextView tvRecentName;
    private TextView tvRecentAuthor;
    private TextView tvRecentRatings;
    private TextView tvRecentReviews;
    private TextView tvRecentHearts;

    public RecentViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        //this.ivRecentPic = itemView.findViewById(R.id.iv_recent_pic);
        //this.ivRecentPic.setClipToOutline(true);
        this.tvRecentName = itemView.findViewById(R.id.tv_recent_name);
        this.tvRecentAuthor = itemView.findViewById(R.id.tv_recent_author);
        //this.tvRecentRatings = itemView.findViewById(R.id.tv_recent_ratings);
        //this.tvRecentReviews = itemView.findViewById(R.id.tv_recent_reviews);
        //this.tvRecentHearts = itemView.findViewById(R.id.tv_recent_hearts);
    }

    public void setIvRecentPic(int p){
        this.ivRecentPic.setImageResource(p);
    }

    public void setTvRecentName(String n){
        this.tvRecentName.setText(n);
    }

    public void setTvRecentAuthor(String n){
        this.tvRecentAuthor.setText(n);
    }

    public void setTvRecentRatings(String r){
        this.tvRecentRatings.setText(r);
    }

    public void setTvRecentReviews(int r){
        this.tvRecentReviews.setText(String.valueOf(r));
    }

    public void setTvRecentHearts(int h){
        this.tvRecentHearts.setText(String.valueOf(h));
    }

    public ImageView getRecentCard(){
        return this.ivRecentPic;
    }
}
