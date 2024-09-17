package com.mobdeve.s14.group4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    private Context context;
    private Order cart;

    public CartAdapter(Context context, Order cart){
        this.context = context;
        this.cart = cart;
    }

    @NonNull
    @NotNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.cart_item_template, parent, false);
        CartViewHolder cartViewHolder = new CartViewHolder(itemView);

        return cartViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CartViewHolder holder, int position) {
        OrderDetails od = cart.getOrderDetails().get(position);

        holder.setOrderDetails(od);
        Picasso.with(holder.itemView.getContext())
                .load(od.getBook().getUploadImage().getmImageUrl())
                .placeholder(R.drawable.perfect_plate_transparent_bg)
                .fit()
                .centerCrop()
                .into(holder.ivCartPic);

        holder.ibCartAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                od.addOne();
                holder.setCartQuantity(od.getQuantity());
                ((AddToCartActivity) context).refreshTotal();
            }
        });

        holder.ibCartSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                od.subOne();
                holder.setCartQuantity(od.getQuantity());
                ((AddToCartActivity) context).refreshTotal();
            }
        });

        holder.ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataHelper.cart.removeOrderDetail(position);
                ((AddToCartActivity) context).refreshTotal();
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.cart.getOrderDetails().size();
    }
}
