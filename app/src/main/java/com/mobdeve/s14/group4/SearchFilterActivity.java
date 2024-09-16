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
import android.widget.Toast;

import java.util.ArrayList;

public class SearchFilterActivity extends AppCompatActivity {
    private TextView tvCategory;
    private ImageButton ibBack;

    private RecyclerView rvFilterCategory;
    private ArrayList<Book> filterBook;
    private RecyclerView.LayoutManager filterCategoryManager;
    private RecentAdapter filterAdapter;
    private TextView tvNoResult;

    private String key;
    private String categ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filter);

        this.tvCategory = findViewById(R.id.tv_category);
        this.ibBack = findViewById(R.id.ib_category_back);
        this.tvNoResult = findViewById(R.id.tv_no_result);

        Intent i = getIntent();

        this.key = i.getStringExtra(DataHelper.KEY_SEARCH);
        this.categ = i.getStringExtra(DataHelper.KEY_CATEGORY);
        Log.d("CATEG", categ);

        this.filterBook = getList();

        if (this.key.length() == 0){
            this.tvCategory.setText(categ + " Books");
        }
        else{
            this.tvCategory.setText("\"" + key + "\" Books");
        }

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

    @Override
    protected void onResume() {
        super.onResume();
        this.refreshList(new CallbackListener() {
            @Override
            public void onSuccess(Object o) {
                filterBook = (ArrayList<Book>) o;
                filterAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure() {
                showError("Failed to reload books");
            }
        });
    }

    private void initFilter(){
        Log.d("FILTER SIZE", String.valueOf(this.filterBook.size()));
        this.rvFilterCategory = findViewById(R.id.rv_filter_category);

        this.filterCategoryManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        this.rvFilterCategory.setLayoutManager(this.filterCategoryManager);

        this.filterAdapter = new RecentAdapter(this.filterBook);
        this.rvFilterCategory.setAdapter(this.filterAdapter);
    }

    private ArrayList<Book> getList(){
        ArrayList<Book> temp = new ArrayList<Book>();

        if (categ.equalsIgnoreCase("ALL")){
            for (Book b : DataHelper.allBooks){
                if (b.getBookName().toLowerCase().contains(key.toLowerCase()))
                    temp.add(b);
            }
        } else {
            for (Book book : DataHelper.allBooks){
                if (book.getCategory().equalsIgnoreCase(categ)){
                    temp.add(book);
                }
            }
        }

        return temp;
    }

    private void refreshList(CallbackListener callbackListener){
        DataHelper.asyncRefreshBooks(this, new CallbackListener() {
            @Override
            public void onSuccess(Object o) {
                callbackListener.onSuccess(getList());
            }

            @Override
            public void onFailure() {
                callbackListener.onFailure();
            }
        });
    }

    private void showError(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}