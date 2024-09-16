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
    private ArrayList<Book> bookList;

    public RecentAdapter(ArrayList<Book> p){
        this.bookList = p;
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
                DataHelper.selectedBook = bookList.get(recentViewHolder.getBindingAdapterPosition());

                Intent i = new Intent(v.getContext(), BookDetailsActivity.class);

                v.getContext().startActivity(i);
            }
        });

        return recentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecentViewHolder holder, int position) {
        holder.setTvRecentName(bookList.get(position).getBookName());
        holder.setTvRecentAuthor(bookList.get(position).getAuthor());
        holder.setTvRecentCategory(bookList.get(position).getCategory());
        holder.setTvRecentPrice(bookList.get(position).getPrice());
        Picasso.with(holder.itemView.getContext())
                .load(bookList.get(position).getUploadImage().getmImageUrl())
                .placeholder(R.drawable.perfect_plate_transparent_bg)
                .fit()
                .centerCrop()
                .into(holder.ivRecentPic);
    }

    @Override
    public int getItemCount() {
        return this.bookList.size();
    }
}
