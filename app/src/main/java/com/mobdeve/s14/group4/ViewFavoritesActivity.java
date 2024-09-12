package com.mobdeve.s14.group4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewFavoritesActivity extends AppCompatActivity {

    private RecyclerView rvBooks;
    private RecyclerView.LayoutManager manager;
    private FavoriteAdapter adapter;

    private ArrayList<Book> data;
    private ImageButton ibBack;
    private TextView tvNoFave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewfavorites);
        
        initRecyclerView();
        initComponents();

        if (data.size() < 1){
            tvNoFave.setVisibility(View.VISIBLE);
        } else {
            tvNoFave.setVisibility(View.GONE);
        }
    }

    private void initComponents() {
        tvNoFave = findViewById(R.id.tv_no_favorites);
        ibBack = findViewById(R.id.ib_favorite_back);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initRecyclerView() {
        rvBooks = findViewById(R.id.viewfavorites_rv_books);

        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvBooks.setLayoutManager(manager);

        data = (ArrayList<Book>) DataHelper.user.getFaveBooks().clone();
        adapter = new FavoriteAdapter(data);
        rvBooks.setAdapter(adapter);
    }
}