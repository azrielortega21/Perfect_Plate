package com.mobdeve.s14.group4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchFilterActivity extends AppCompatActivity {
    private TextView tvCategory;
    private ImageButton ibBack;
    private CardView cvFood;
    private RecyclerView rvFilterCategory;
    private ArrayList<Book> filterBook;
    private RecyclerView.LayoutManager filterCategoryManager;
    private RecentAdapter filterAdapter;
    private TextView tvNoResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filter);

        this.tvCategory = findViewById(R.id.tv_category);
        this.ibBack = findViewById(R.id.ib_category_back);
        this.tvNoResult = findViewById(R.id.tv_no_result);
        this.filterBook = new ArrayList<Book>();

        Intent i = getIntent();

        String categ = i.getStringExtra(SearchActivity.KEY_CATEGORY);
        String key = i.getStringExtra(SearchActivity.KEY_SEARCH);
        Log.d("CATEG", categ);

        if (categ.equalsIgnoreCase("All Recipes")){
            filterBook = DataHelper.allBooks;
        } else {
            for (Book book : DataHelper.allBooks){
                if (book.getCategory().equalsIgnoreCase(categ)){
                    filterBook.add(book);
                }
            }
        }

        if(!key.equalsIgnoreCase("-9999")){
            for (Book book : DataHelper.allBooks){
                String tempName = book.getBookName().toLowerCase();
                String tempKey = key.toLowerCase();

                if (tempName.contains(tempKey)){
                    filterBook.add(book);
                }
            }
        }

        String temp;
        if (!categ.equalsIgnoreCase("-9999")){
            temp = "'" + categ + "'";
        } else {
            temp = "'" + key + "'";
        }

        this.tvCategory.setText(temp);

        this.ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.initFilter();

        if(this.filterBook.size() == 0){
            tvNoResult.setVisibility(View.VISIBLE);
        } else {
            tvNoResult.setVisibility(View.GONE);
        }
    }

    private void initFilter(){

        Log.d("FILTER SIZE", String.valueOf(this.filterBook.size()));
        this.rvFilterCategory = findViewById(R.id.rv_filter_category);

        this.filterCategoryManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        this.rvFilterCategory.setLayoutManager(this.filterCategoryManager);

        this.filterAdapter = new RecentAdapter(this.filterBook);
        this.rvFilterCategory.setAdapter(this.filterAdapter);
    }

}