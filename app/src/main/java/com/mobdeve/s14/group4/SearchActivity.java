package com.mobdeve.s14.group4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private LinearLayout llProfile;
    private LinearLayout llHome;
    private LinearLayout llCreate;
    private LinearLayout llFavorites;

    private TextView tvViewAll;
    private CardView cvCategory;
    private CardView cvPasta;
    private CardView cvMain;
    private CardView cvDessert;
    private CardView cvPastry;
    private CardView cvDrinks;

    private EditText etSearch;

    private ArrayList<Book> bookFilter;



    private SearchView svSearch;


    public static final String KEY_CATEGORY = "KEY_CATEGORY";
    public static final String KEY_SEARCH = "KEY_SEARCH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initComponents();


    }


    private void initComponents() {
        this.llProfile = findViewById(R.id.ll_profile);
        this.llHome = findViewById(R.id.ll_home);
        llCreate = findViewById(R.id.ll_create);
        llFavorites = findViewById(R.id.ll_fav);
        this.cvCategory = findViewById(R.id.cv_search_appetizer);
        this.cvPasta = findViewById(R.id.cv_search_pasta);
        this.cvMain = findViewById(R.id.cv_search_main);
        this.cvDessert = findViewById(R.id.cv_search_dessert);
        this.cvPastry = findViewById(R.id.cv_search_pastry);
        this.cvDrinks = findViewById(R.id.cv_search_drinks);
        this.tvViewAll = findViewById(R.id.tv_search_view_all);

        this.svSearch = findViewById(R.id.sv_search);
        this.etSearch = findViewById(R.id.et_search_book_search);

        this.bookFilter = new ArrayList<Book>();


        llFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (SearchActivity.this, ViewFavoritesActivity.class);
                startActivity(i);
            }
        });

        this.llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });

        this.llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.llCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(SearchActivity.this, CreateRecipeActivity1.class);
//                startActivity(i);
            }
        });

        this.cvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchActivity.this, SearchFilterActivity.class);
                i.putExtra(KEY_CATEGORY, "Appetizer");
                i.putExtra(KEY_SEARCH, "99999");
                startActivity(i);

            }
        });

        this.cvPasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchActivity.this, SearchFilterActivity.class);
                i.putExtra(KEY_CATEGORY, "Pasta");
                i.putExtra(KEY_SEARCH, "99999");
                startActivity(i);

            }
        });

        this.cvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchActivity.this, SearchFilterActivity.class);
                i.putExtra(KEY_CATEGORY, "Main");
                i.putExtra(KEY_SEARCH, "-9999");
                startActivity(i);

            }
        });

        this.cvDessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchActivity.this, SearchFilterActivity.class);
                i.putExtra(KEY_CATEGORY, "Dessert");
                i.putExtra(KEY_SEARCH, "-9999");
                startActivity(i);

            }
        });

        this.cvPastry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchActivity.this, SearchFilterActivity.class);
                i.putExtra(KEY_CATEGORY, "Pastry");
                i.putExtra(KEY_SEARCH, "-9999");
                startActivity(i);

            }
        });

        this.cvDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchActivity.this, SearchFilterActivity.class);
                i.putExtra(KEY_CATEGORY, "Drinks");
                i.putExtra(KEY_SEARCH, "-9999");
                startActivity(i);

            }
        });

        this.tvViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchActivity.this, SearchFilterActivity.class);
                i.putExtra(KEY_CATEGORY, "All Recipes");
                i.putExtra(KEY_SEARCH, "-9999");
                startActivity(i);

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
                   Intent i = new Intent(SearchActivity.this, SearchFilterActivity.class);
                   i.putExtra(KEY_SEARCH, key);
                   i.putExtra(KEY_CATEGORY, "-9999");
                   etSearch.setText("");
                   startActivity(i);
                   return true;
               }
               return false;
           }
       });
    }


}