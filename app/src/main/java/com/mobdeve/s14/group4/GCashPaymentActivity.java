package com.mobdeve.s14.group4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GCashPaymentActivity extends AppCompatActivity {
    private TextView tvTotal;
    private Button btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcash_payment);

        this.tvTotal = findViewById(R.id.tv_total_payment);
        this.btnDone = findViewById(R.id.btn_gcash_done);

        this.tvTotal.setText(String.format("%.2f", DataHelper.user.getCart().getTotal()));

        this.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GCashPaymentActivity.this, CheckoutDoneActivity.class);
                startActivity(i);
            }
        });
    }
}