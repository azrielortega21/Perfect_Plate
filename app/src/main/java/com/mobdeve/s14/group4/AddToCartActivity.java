package com.mobdeve.s14.group4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddToCartActivity extends AppCompatActivity {
    private TextView tvTotal;
    private ImageButton ibBack;
    private ConstraintLayout clCheckout;

    private RecyclerView rvCart;
    private RecyclerView.LayoutManager rvCartManager;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        this.tvTotal = findViewById(R.id.tv_cart_total);
        this.ibBack = findViewById(R.id.ib_cart_back);
        this.clCheckout = findViewById(R.id.cl_cart_checkout);

        this.ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.clCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if items > 0, proceed to checkout
                ArrayList<OrderDetails> odList = new ArrayList<OrderDetails>();
                for (OrderDetails od : DataHelper.user.getCart().getOrderDetails()){
                    if (od.getQuantity() > 0){
                        odList.add(od);
                    }
                }

                if (odList.size() > 0){
                    DataHelper.user.setCart(odList);
                    DataHelper.user.refreshCartInfo();
                    Intent i = new Intent(AddToCartActivity.this, CheckoutActivity.class);
                    startActivity(i);
                }
                else{
                    showError("No items in cart!");
                }
            }
        });

        initCart();
        refreshTotal();
    }

    @Override
    protected void onResume() {
        super.onResume();

        int i = 0;
        for (OrderDetails od : DataHelper.user.getCart().getOrderDetails()){
            int position = i++;
            Book b = od.getBook();

            DataHelper.refreshStock(b.getId(), new CallbackListener() {
                @Override
                public void onSuccess(Object o) {
                    b.setStock((int) o);
                    cartAdapter.notifyItemChanged(position);
                }

                @Override
                public void onFailure() {
                    //failed to refresh stock
                }
            });
        }
    }

    private void initCart(){
        this.rvCart = findViewById(R.id.rv_cart_items);

        this.rvCartManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        this.cartAdapter = new CartAdapter(this);

        this.rvCart.setLayoutManager(rvCartManager);
        this.rvCart.setAdapter(cartAdapter);
    }

    public void refreshTotal(){
        this.tvTotal.setText(String.format("%.2f", DataHelper.user.getCart().getTotal()));
    }

    private void showError(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}