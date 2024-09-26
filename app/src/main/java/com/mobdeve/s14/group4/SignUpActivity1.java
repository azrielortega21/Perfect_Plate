package com.mobdeve.s14.group4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class SignUpActivity1 extends AppCompatActivity {

    private Button btnNext;

    private TextView tvSignIn;

    private FirebaseAuth mAuth;
    //private FirebaseDatabase mDatabase;

    private EditText etEmail;
    private EditText etPassword;
    private EditText etFullName;

    private String email;
    private String password;
    private String fullName;

    public static final String KEY_EMAIL = "KEY_EMAIL";
    public static final String KEY_FULLNAME = "KEY_FULLNAME";
    public static final String KEY_PASSWORD = "KEY_PASSWORD";


    private void initFirebase() {
        this.mAuth = FirebaseAuth.getInstance();
        //this.mDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup1);

        btnNext = findViewById(R.id.signup1_btn_next);

        tvSignIn = findViewById(R.id.signup1_tv_signin);

        etEmail = findViewById(R.id.signup1_et_email);
        etPassword = findViewById(R.id.signup1_et_password);
        etFullName = findViewById(R.id.signup1_et_fullName);


        this.initFirebase();

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        this.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                fullName = etFullName.getText().toString().trim();

                Log.d("TEST EMAIL SIGN UP 1", email);
                Log.d("TEST PASSWORD SIGN UP 1", password);
                Log.d("TEST USERNAME SIGN UP 1", fullName);


                Intent i = new Intent(SignUpActivity1.this, SignUpActivity2.class);
                i.putExtra(KEY_EMAIL, email);
                i.putExtra(KEY_FULLNAME, fullName);
                i.putExtra(KEY_PASSWORD, password);


                SignUpActivity1.this.startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
}