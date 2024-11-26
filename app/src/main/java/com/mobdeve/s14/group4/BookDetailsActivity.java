package com.mobdeve.s14.group4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class BookDetailsActivity extends AppCompatActivity {
    private Book book;

    private TextView tvBookName;
    private ImageView ivBookPic;
    private TextView tvAuthors;
    private TextView tvCategory;

    private TextView tvStock;
    private TextView tvPrice;

    private ImageButton ibBack;
    private ImageButton ibCart;
    private Button btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        this.tvBookName = findViewById(R.id.tv_details_title);
        this.ivBookPic = findViewById(R.id.iv_details_book);
        this.tvAuthors = findViewById(R.id.tv_details_authors);
        this.tvCategory = findViewById(R.id.tv_details_category);

        this.tvStock = findViewById(R.id.tv_deatils_available);
        this.tvPrice = findViewById(R.id.tv_details_price);

        this.ibBack = findViewById(R.id.ib_details_back);
        this.ibCart = findViewById(R.id.ib_details_cart);

        this.btnAddToCart = findViewById(R.id.btn_details_addCart);

        Intent i = getIntent();
        String id = i.getStringExtra(DataHelper.KEY_BOOK_ID);

        this.book = DataHelper.bookDatabase.findBook(id);

        this.tvBookName.setText(book.getBookName());
        this.tvAuthors.setText(book.getAuthor());
        this.tvCategory.setText(book.getCategory());
        this.tvStock.setText(String.valueOf(book.getStock()));
        this.tvPrice.setText(String.format("%.2f", book.getPrice()));

        //set pic
        Picasso.with(this)
                .load(book.getUploadImage().getmImageUrl())
                .placeholder(R.drawable.perfect_plate_transparent_bg)
                .fit()
                .centerCrop()
                .into(this.ivBookPic);

        this.ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.ibCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BookDetailsActivity.this, AddToCartActivity.class);
                startActivity(i);
            }
        });

        this.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderDetails od = new OrderDetails(book.getId(), 1, book);
                DataHelper.cart.addOrderDetail(od);
                showMessage("Added to Cart");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // TODO: sync stock
//        loadStock();
    }

    private void showMessage(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}