package com.mobdeve.s14.group4;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecentAdapter extends RecyclerView.Adapter<RecentViewHolder>{
    private ArrayList<Book> foodList;

    public static final String KEY_BOOK_ID = "KEY_BOOK_ID";

    public RecentAdapter(ArrayList<Book> p){
        this.foodList = p;
    }

    @NonNull
    @NotNull
    @Override
    public RecentViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.recent_template, parent, false);

        RecentViewHolder recentViewHolder = new RecentViewHolder(itemView);

        recentViewHolder.getRecentCard().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(), BookDetailsActivity.class);
                i.putExtra(KEY_BOOK_ID, foodList.get(recentViewHolder.getBindingAdapterPosition()).getId());

                v.getContext().startActivity(i);

            }
        });

        return recentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecentViewHolder holder, int position) {
        holder.setIvRecentPic(foodList.get(position).getBookPic());
        holder.setTvRecentName(foodList.get(position).getBookName());
//        holder.setTvRecentAuthor(foodList.get(position).getContributorName());
        holder.setTvRecentRatings(foodList.get(position).getRatingString());
        holder.setTvRecentReviews(foodList.get(position).getReviewCount());
        holder.setTvRecentHearts(foodList.get(position).getFaveCount());
        Picasso.with(holder.itemView.getContext())
                .load(foodList.get(position).getUploadImage().getmImageUrl())
                .placeholder(R.drawable.perfect_plate_transparent_bg)
                .fit()
                .centerCrop()
                .into(holder.ivRecentPic);


    }

    @Override
    public int getItemCount() {
        return this.foodList.size();
    }
}
