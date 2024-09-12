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

public class PopularAdapter extends RecyclerView.Adapter<PopularViewHolder> {
    private ArrayList<Book> foodList;

    public static final String KEY_BOOK_ID = "KEY_BOOK_ID";

    public PopularAdapter(ArrayList<Book> p){
        this.foodList = p;
    }


    @NonNull
    @NotNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.popular_template, parent, false);

        PopularViewHolder popularViewHolder = new PopularViewHolder(itemView);

        popularViewHolder.getCvPopularCard().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(), BookDetailsActivity.class);
                i.putExtra(KEY_BOOK_ID, foodList.get(popularViewHolder.getBindingAdapterPosition()).getId());

                v.getContext().startActivity(i);

            }
        });
        return popularViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PopularViewHolder holder, int position) {
        //holder.setIvPopularPic(foodList.get(position).getRecipePic());
        holder.setTvPopularName(foodList.get(position).getBookName());
        System.out.println("TESTPIC" + foodList.get(position).getUploadImage().getmImageUrl());
        Picasso.with(holder.itemView.getContext())
                .load(foodList.get(position).getUploadImage().getmImageUrl())
                .placeholder(R.drawable.perfect_plate_transparent_bg)
                .fit()
                .centerCrop()
                .into(holder.ivPopularPic);
    }

    @Override
    public int getItemCount() {
        return this.foodList.size();
    }
}
