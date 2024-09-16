package com.mobdeve.s14.group4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AdminAccessActivity extends AppCompatActivity {

    private FloatingActionButton fabAddAdmin;

    private ImageButton ibBack;

    private RecyclerView rvAdmins;
    private RecyclerView.LayoutManager adminManager;
    private AdminAdapter adminAdapter;

    private ArrayList<User> admins;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_access);

        initComponents();
    }

    private void initComponents() {

        fabAddAdmin = findViewById(R.id.fab_add_admin);

        ibBack = findViewById(R.id.ib_admin_back);

        loadAllAdmins();

        fabAddAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminAccessActivity.this, AddAdminActivity.class);
                startActivity(i);
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadAllAdmins() {
        new UserDatabase().getAllAdminsIRT(new CallbackListener() {
            @Override
            public void onSuccess(Object o) {
                admins = (ArrayList<User>) o;
                initRVAdmins();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void initRVAdmins(){
        Log.d("Admins", String.valueOf(this.admins.size()));
        rvAdmins = findViewById(R.id.rv_admin_list);

        adminManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvAdmins.setLayoutManager(adminManager);

        adminAdapter = new AdminAdapter(admins);
        rvAdmins.setAdapter(adminAdapter);
    }
}