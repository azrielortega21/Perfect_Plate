package com.mobdeve.s14.group4;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class CheckoutViewHolder extends RecyclerView.ViewHolder {
    public ImageView ivBookPic;
    private TextView tvTitle;
    private TextView tvCategory;
    private TextView tvPrice;
    private TextView tvQuantity;

    public CheckoutViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        this.ivBookPic = itemView.findViewById(R.id.iv_checkout_pic);
        this.tvTitle = itemView.findViewById(R.id.tv_checkout_title);
        this.tvCategory = itemView.findViewById(R.id.tv_checkout_category);
        this.tvPrice = itemView.findViewById(R.id.tv_checkout_price);
        this.tvQuantity = itemView.findViewById(R.id.tv_checkout_qty);
    }

    public void setTitle(String title){
        this.tvTitle.setText(title);
    }

    public void setCategory(String category){
        this.tvCategory.setText(category);
    }

    public void setPrice(double price){
        this.tvPrice.setText(String.format("%.2f", price));
    }

    public void setQuantity(int quantity){
        this.tvQuantity.setText(String.valueOf(quantity));
    }
}
