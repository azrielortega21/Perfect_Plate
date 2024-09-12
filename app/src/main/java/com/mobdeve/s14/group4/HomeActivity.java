package com.mobdeve.s14.group4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvPopular;
    private ArrayList<Book> popularBookList;
    private RecyclerView rvRecent;
    private ArrayList<Book> recentBookList;
    private ArrayList<Review> reviewList;

    private RecyclerView.LayoutManager popularManager;
    private PopularAdapter popularAdapter;
    private RecyclerView.LayoutManager recentManager;
    private RecentAdapter recentAdapter;

    private LinearLayout llProfile;
    private LinearLayout llSearch;
    private LinearLayout llCreate;
    private LinearLayout llFavorites;

    private ImageView ivSearch;
    private EditText etSearch;

    public static final String KEY_CATEGORY = "KEY_CATEGORY";
    public static final String KEY_SEARCH = "KEY_SEARCH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d("HELLO", "HELLO");

        this.initComponents(); // init components and load data

        this.initPopularRecyclerView();
        this.initRecentFeed();
    }

    @Override
    protected void onResume() {
        super.onResume();

        final CallbackListener recipesListener = new CallbackListener() {
            @Override
            public void onSuccess(Object o) {
                refreshAdapters();
            }

            @Override
            public void onFailure() {

            }
        };

        DataHelper.asyncRefreshDatabase(getApplicationContext(), recipesListener);

    }

    private void initComponents(){
        this.llProfile = findViewById(R.id.ll_profile);
        this.llSearch = findViewById(R.id.ll_search);
        this.llCreate = findViewById(R.id.ll_create);
        this.ivSearch = findViewById(R.id.iv_search);
        this.llFavorites = findViewById(R.id.ll_fav);
        this.reviewList = DataHelper.allReviews;

        this.etSearch = findViewById(R.id.et_search_book);

//        Log.d("LISTSIZE", String.valueOf(recipeList.size()));
        //initRecipes

        llFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (HomeActivity.this, ViewFavoritesActivity.class);
                startActivity(i);
            }
        });

        this.llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });

        this.llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });

        this.llCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(HomeActivity.this, CreateRecipeActivity1.class);
//                startActivity(i);
            }
        });

        this.etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    Log.d("SEARCHTEST", "enter pressed");
                    String key = etSearch.getText().toString();
                    Intent i = new Intent(HomeActivity.this, SearchFilterActivity.class);
                    etSearch.setText("");
                    i.putExtra(KEY_SEARCH, key);
                    i.putExtra(KEY_CATEGORY, "-9999");
                    startActivity(i);
                    return true;
                }
                return false;
            }
        });

//        Log.d("RECIPE LIST SIZE aaaaaa", String.valueOf(recipeList.size()));
    }

    private void refreshAdapters(){
        this.recentBookList.clear();
        this.recentBookList.addAll(DataHelper.allBooks);
        this.recentAdapter.notifyDataSetChanged();

        this.popularBookList.clear();
        this.popularBookList.addAll(DataHelper.popularBooks);
        this.popularAdapter.notifyDataSetChanged();
    }

    private void initPopularRecyclerView(){
        this.popularBookList = DataHelper.popularBooks;
        this.rvPopular = findViewById(R.id.rv_home_popular);

        this.popularManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        this.rvPopular.setLayoutManager(this.popularManager);

        this.popularAdapter = new PopularAdapter(this.popularBookList);
        this.rvPopular.setAdapter(this.popularAdapter);
    }

    private void initRecentFeed(){
        this.recentBookList = DataHelper.allBooks;
        this.rvRecent = findViewById(R.id.rv_home_recent);

        this.recentManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        this.rvRecent.setLayoutManager(recentManager);

        this.recentAdapter = new RecentAdapter(this.recentBookList);
        this.rvRecent.setAdapter(recentAdapter);
    }
}