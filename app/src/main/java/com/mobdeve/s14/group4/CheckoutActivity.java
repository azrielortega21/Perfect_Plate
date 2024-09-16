package com.mobdeve.s14.group4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {
    public TextView tvCustomer;
    public TextView tvAddressStreet;
    public TextView tvAddressArea;
    public TextView tvTotal;

    public RecyclerView rvOrder;
    public RecyclerView.LayoutManager rvOrderManager;
    public CheckoutAdapter checkoutAdapter;

    public ImageButton ibBack;
    public Button btnCOD;
    public Button btnGCash;
    public Button btnPlaceOrder;

    private boolean isCOD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        this.tvCustomer = findViewById(R.id.tv_checkout_name);
        this.tvAddressStreet = findViewById(R.id.tv_checkout_street_address);
        this.tvAddressArea = findViewById(R.id.tv_city_province_postal);
        this.tvTotal = findViewById(R.id.tv_checkout_total);

        this.ibBack = findViewById(R.id.ib_checkout_back);
        this.btnCOD = findViewById(R.id.btn_checkout_cod);
        this.btnGCash = findViewById(R.id.btn_checkout_gcash);
        this.btnPlaceOrder = findViewById(R.id.btn_checkout_place_order);

        this.rvOrder = findViewById(R.id.rv_checkout_summary);
        this.rvOrderManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        this.checkoutAdapter = new CheckoutAdapter(DataHelper.user.getCart().getOrderDetails());

        this.rvOrder.setLayoutManager(this.rvOrderManager);
        this.rvOrder.setAdapter(this.checkoutAdapter);

        setDetails();

        if (DataHelper.user.getCart().getModeOfPay().equals("COD")){
            this.isCOD = true;
            setActive(btnCOD);
        }
        else{
            this.isCOD = false;
            setActive(btnGCash);
        }

        this.ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.btnCOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCOD){
                    isCOD = true;
                    DataHelper.user.getCart().setCOD();
                    setInactive(btnGCash);
                    setActive(btnCOD);
                }
            }
        });

        this.btnGCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCOD){
                    isCOD = false;
                    DataHelper.user.getCart().setGCash();
                    setInactive(btnCOD);
                    setActive(btnGCash);
                }
            }
        });

        this.btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataHelper.orderDatabase.placeOrder(DataHelper.user.getCart(), new CallbackListener() {
                    @Override
                    public void onSuccess(Object o) {
                        ArrayList<OrderDetails> failed = (ArrayList<OrderDetails>) o;

                        if (failed.size() > 0){
                            //TODO: add notification for failure to buy due to excess
                        }

                        DataHelper.user.setCart(failed);
                        goToNextActivity();
                    }

                    @Override
                    public void onFailure() {
                        showError("Database error");
                    }
                });
            }
        });
    }

    private void setActive(Button b){
        b.setBackgroundColor(getResources().getColor(R.color.mobile_gray));
        b.setTextColor(getResources().getColor(R.color.white));
    }

    private void setInactive(Button b){
        b.setBackgroundColor(getResources().getColor(R.color.white));
        b.setTextColor(getResources().getColor(R.color.mobile_gray));
    }

    private void setDetails(){
        this.tvCustomer.setText(DataHelper.user.getFullName());

        Address address = DataHelper.user.getAddress();
        this.tvAddressStreet.setText(address.getStreet());
        this.tvAddressArea.setText(address.getCity() + ", " + address.getState() + " " + address.getPostalCode());

        this.tvTotal.setText(String.format("%.2f", DataHelper.user.getCart().getTotal()));
    }

    private void showError(String error){
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    private void goToNextActivity(){
        Intent i;

        if (isCOD){
            i = new Intent(CheckoutActivity.this, CheckoutDoneActivity.class);
        }
        else{
            i = new Intent(CheckoutActivity.this, GCashPaymentActivity.class);
        }

        startActivity(i);
    }
}