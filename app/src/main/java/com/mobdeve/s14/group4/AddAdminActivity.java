package com.mobdeve.s14.group4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddAdminActivity extends AppCompatActivity {

    private EditText etEmail;

    private Button btnCancel;
    private Button btnSave;

    private boolean valid = false;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        initComponents();
    }

    private void initComponents() {

        etEmail = findViewById(R.id.et_admin_new_email);

        btnCancel = findViewById(R.id.btn_admin_cancel);
        btnSave = findViewById(R.id.btn_add_admin);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validateInfo
                btnSave.setEnabled(false);
                String email = etEmail.getText().toString().trim();

                if(email.isEmpty()){
                    etEmail.setError("Field is Required");
                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    etEmail.setError("Email is invalid");
                }
                else{
                    makeAdmin(email);
                }
                btnSave.setEnabled(true);
            }
        });
    }

    private void makeAdmin(String email) {
        new UserDatabase().getAllUsers(new CallbackListener() {
            @Override
            public void onSuccess(Object o) {
                ArrayList<User> users = (ArrayList<User>) o;
                boolean found = false;
                for (User u : users){
                    if(u.getEmail().equals(email)){
                        found = true;
                        if(u.isAdmin()){
                            message = "User is already Admin";
                        }
                        else{
                            u.setAdmin(true);
                            message = "Added Admin Success!";
                            new UserDatabase().updateUser(u);
                            valid = true;
                        }
                        break;
                    }
                }

                if(!found) message = "User is not found";
                conclude();
            }

            @Override
            public void onFailure() {
                conclude();
            }
        });
    }

    private void conclude() {
        if (valid){
            Toast.makeText(AddAdminActivity.this, message, Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(AddAdminActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    }
}