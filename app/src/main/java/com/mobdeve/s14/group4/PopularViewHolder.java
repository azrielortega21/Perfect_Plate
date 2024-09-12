package com.mobdeve.s14.group4;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class PopularViewHolder extends RecyclerView.ViewHolder {
    public ImageView ivPopularPic;
    private TextView tvPopularName;
    private CardView cvPopularCard;

    //private TextView tvPopularLabel;


    public PopularViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
//        this.ivPopularPic = itemView.findViewById(R.id.iv_popular_pic);
        this.tvPopularName = itemView.findViewById(R.id.tv_popular_name);
        this.cvPopularCard = itemView.findViewById(R.id.cv_popular_card);

    }

//    public void setIvPopularPic(int p){
//        this.ivPopularPic.setImageResource(p);
//    }

    public void setTvPopularName(String n){
        this.tvPopularName.setText(n);
    }
    public CardView getCvPopularCard(){
        return this.cvPopularCard;
    }
}
