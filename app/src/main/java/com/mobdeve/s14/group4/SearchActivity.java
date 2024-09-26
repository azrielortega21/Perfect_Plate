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
    private LinearLayout llCart;
    private LinearLayout llSearch;


    private CardView cvGrade1;
    private CardView cvGrade2;
    private CardView cvGrade3;
    private CardView cvGrade4;
    private CardView cvGrade5;
    private CardView cvGrade6;
    private CardView cvGrade7;
    private CardView cvGrade8;
    private CardView cvGrade9;
    private CardView cvGrade10;
    private CardView cvAll;






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
        this.llCart = findViewById(R.id.ll_cart);
        this.llSearch  =findViewById(R.id.ll_search);

        this.cvGrade1 = findViewById(R.id.cv_search_grade1);
        this.cvGrade2 = findViewById(R.id.cv_search_grade2);
        this.cvGrade3 = findViewById(R.id.cv_search_grade3);
        this.cvGrade4 = findViewById(R.id.cv_search_grade4);
        this.cvGrade5 = findViewById(R.id.cv_search_grade5);
        this.cvGrade6 = findViewById(R.id.cv_search_grade6);
        this.cvGrade7 = findViewById(R.id.cv_search_grade7);
        this.cvGrade8 = findViewById(R.id.cv_search_grade8);
        this.cvGrade9 = findViewById(R.id.cv_search_grade9);
        this.cvGrade10 = findViewById(R.id.cv_search_grade10);
        this.cvAll = findViewById(R.id.cv_search_all);

        this.svSearch = findViewById(R.id.sv_search);
        this.etSearch = findViewById(R.id.et_search_book_search);

        this.bookFilter = new ArrayList<Book>();



        this.llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });

        //TODO: do this until Grade 10
        this.cvGrade1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchActivity.this, SearchFilterActivity.class);
                i.putExtra(KEY_CATEGORY, "Grade 1");
                i.putExtra(KEY_SEARCH, "99999");
                startActivity(i);

            }
        });

        // add here for grade 2-10


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