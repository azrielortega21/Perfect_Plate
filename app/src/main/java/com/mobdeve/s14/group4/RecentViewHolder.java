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
    private TextView tvRecentCategory;
    private TextView tvRecentPrice;

    public RecentViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        this.ivRecentPic = itemView.findViewById(R.id.iv_recent_pic);
        this.ivRecentPic.setClipToOutline(true);
        this.tvRecentName = itemView.findViewById(R.id.tv_recent_name);
        this.tvRecentAuthor = itemView.findViewById(R.id.tv_recent_author);
        this.tvRecentPrice = itemView.findViewById(R.id.tv_recent_price);
        this.tvRecentCategory = itemView.findViewById(R.id.tv_recent_category);
    }

    public ImageView getRecentCard(){
        return this.ivRecentPic;
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

    public void setTvRecentCategory(String c) {
        this.tvRecentCategory.setText(c);
    }

    public void setTvRecentPrice(double price) {
        this.tvRecentPrice.setText(String.format("%.2f", price));
    }
}
