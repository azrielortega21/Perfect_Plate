package com.mobdeve.s14.group4;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteBookHolder> {

    public static final String KEY_BOOK_ID = "KEY_BOOK_ID";

    private ArrayList<Book> data;

    private CardView cvBook;

    public FavoriteAdapter(ArrayList<Book> tempData){
        data = tempData;
    }


    @NonNull
    @NotNull
    @Override
    public FavoriteBookHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.template_favoritebook, parent, false);
        FavoriteBookHolder holder = new FavoriteBookHolder(view);

        holder.getCard().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), BookDetailsActivity.class);
                i.putExtra(KEY_BOOK_ID, data.get(holder.getBindingAdapterPosition()).getId());
                v.getContext().startActivity(i);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FavoriteBookHolder holder, int position) {
        holder.setBook(data.get(position));
        holder.setBookName(data.get(position).getBookName());
        Picasso.with(holder.itemView.getContext())
                .load(data.get(position).getUploadImage().getmImageUrl())
                .placeholder(R.drawable.perfect_plate_transparent_bg)
                .fit()
                .centerCrop()
                .into(holder.ivBook);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
