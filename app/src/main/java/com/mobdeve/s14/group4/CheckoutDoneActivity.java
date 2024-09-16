package com.mobdeve.s14.group4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CheckoutDoneActivity extends AppCompatActivity {
    private Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_done);

        this.btnOk = findViewById(R.id.button);

        this.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CheckoutDoneActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });
    }
}