package com.mobdeve.s14.group4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1;

    private Button btnLogin;

    private EditText etEmail;
    private EditText etPassword;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth.AuthStateListener authStateListener;

    private ProgressBar pbLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.initComponents();
        this.initFirebase();
    }

    private void initFirebase(){
        this.mDatabase = FirebaseDatabase.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
        this.databaseReference = mDatabase.getReference("users");

    }

    private void initComponents(){
        this.btnLogin = findViewById(R.id.login_btn_login);
        this.etEmail = findViewById(R.id.login_et_email);
        this.etPassword = findViewById(R.id.login_et_password);
        pbLogin = findViewById(R.id.login_pb);



        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbLogin.setVisibility(View.VISIBLE);
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (!isEmpty(email, password)) {
                    signIn(email, password);
                }
            }
        });
    }

    private boolean isEmpty(String e, String p){

        if(e.isEmpty()){
            this.etEmail.setError("Required field");
            this.etEmail.requestFocus();
            return true;
        }
        return false;
    }

    private void signIn(String email, String password) {
        // this.pbLogin.setVisibility(View.VISIBLE); ADD PROGRESS BAR LATER

        this.mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                       @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                           successfulLogin();
                        } else {
                            failedLogin();
                        }
                    }
                });
    }

    private void successfulLogin(){
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                pbLogin.setVisibility(View.GONE);
                if (user != null) {
                    // User is signed in
                    DataHelper.loadUser(user.getUid());
                    Log.d("SIGNED IN LOGGED IN", "onAuthStateChanged:signed_in:" + user.getUid());

                    moveToSearchActivity();
                    finish();
                } else {
                    // User is signed out
                    Log.d("TAG", "onAuthStateChanged:signed_out");
                }
            }
        };

        authStateListener.onAuthStateChanged(FirebaseAuth.getInstance());
    }

    private void failedLogin(){
        //this.btnNext.setVisibility(View.GONE);
        Toast.makeText(this, "LOG IN FAIL", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);

        finish();

    }

    private void moveToSearchActivity(){
        Intent i = new Intent(LoginActivity.this, SearchActivity.class);
        startActivity(i);
    }
}