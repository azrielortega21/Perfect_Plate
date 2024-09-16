package com.mobdeve.s14.group4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutViewHolder>{
    private ArrayList<OrderDetails> orderDetails;

    public CheckoutAdapter(ArrayList<OrderDetails> orderDetails){
        this.orderDetails = orderDetails;
    }

    @NonNull
    @NotNull
    @Override
    public CheckoutViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.checkout_template, parent, false);

        CheckoutViewHolder checkoutViewHolder = new CheckoutViewHolder(itemView);

        return checkoutViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CheckoutViewHolder holder, int position) {
        holder.setTitle(orderDetails.get(position).getBook().getBookName());
        holder.setCategory(orderDetails.get(position).getBook().getCategory());
        holder.setPrice(orderDetails.get(position).getBook().getPrice());
        holder.setQuantity(orderDetails.get(position).getQuantity());

        Picasso.with(holder.itemView.getContext())
                .load(orderDetails.get(position).getBook().getUploadImage().getmImageUrl())
                .placeholder(R.drawable.perfect_plate_transparent_bg)
                .fit()
                .centerCrop()
                .into(holder.ivBookPic);
    }

    @Override
    public int getItemCount() {
        return this.orderDetails.size();
    }
}
