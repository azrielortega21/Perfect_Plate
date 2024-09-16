package com.mobdeve.s14.group4;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class CartViewHolder extends RecyclerView.ViewHolder {
    public ImageView ivCartPic;
    private TextView tvCartTitle;
    private TextView tvCartCategory;
    private TextView tvCartQuantity;
    private TextView tvOutOfStock;

    public ImageButton ibCartAdd;
    public ImageButton ibCartSub;
    public ImageButton ibDelete;

    private OrderDetails orderDetails;

    public CartViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        this.ivCartPic = itemView.findViewById(R.id.iv_cart_pic);
        this.ivCartPic.setClipToOutline(true);
        this.tvCartTitle = itemView.findViewById(R.id.tv_cart_title);
        this.tvCartCategory = itemView.findViewById(R.id.tv_cart_category);
        this.tvCartQuantity = itemView.findViewById(R.id.tv_cart_qty);

        this.ibCartAdd = itemView.findViewById(R.id.ib_cart_add);
        this.ibCartSub = itemView.findViewById(R.id.ib_cart_sub);
        this.ibDelete = itemView.findViewById(R.id.ib_cart_delete);
        this.tvOutOfStock = itemView.findViewById(R.id.tv_cart_outofstock);
    }

    public void setOrderDetails(OrderDetails orderDetails){
        this.orderDetails = orderDetails;

        setCartTitle(orderDetails.getBook().getBookName());
        setCartCategory(orderDetails.getBook().getCategory());
        setCartQuantity(orderDetails.getQuantity());

        int stock = orderDetails.getBook().getStock();
        if (orderDetails.getQuantity() > stock){
            if (stock <= 0){
                this.tvOutOfStock.setText("out of stock");
            }
            else{
                this.tvOutOfStock.setText("not enough stock");
            }

            this.tvOutOfStock.setVisibility(View.VISIBLE);
        }
        else{
            this.tvOutOfStock.setVisibility(View.GONE);
        }
    }

    public void setCartTitle(String title){
        this.tvCartTitle.setText(title);
    }

    public void setCartCategory(String category){
        this.tvCartCategory.setText(category);
    }

    public void setCartQuantity(int quantity){
        this.tvCartQuantity.setText(String.valueOf(quantity));
    }
}
