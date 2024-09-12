package com.mobdeve.s14.group4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class AddToCartActivity extends AppCompatActivity {
    private TextView tvTotal;
    private ImageButton ibBack;

    private RecyclerView rvCart;
    private RecyclerView.LayoutManager rvCartManager;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        this.tvTotal = findViewById(R.id.tv_cart_total);
        this.ibBack = findViewById(R.id.ib_cart_back);

        this.ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initCart();
        refreshTotal();
    }

    private void initCart(){
        this.rvCart = findViewById(R.id.rv_cart_items);

        this.rvCartManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        this.cartAdapter = new CartAdapter(this, DataHelper.cart);

        this.rvCart.setLayoutManager(rvCartManager);
        this.rvCart.setAdapter(cartAdapter);
    }

    public void refreshTotal(){
        this.tvTotal.setText(String.format("%.2f", DataHelper.cart.getTotal()));
    }
}