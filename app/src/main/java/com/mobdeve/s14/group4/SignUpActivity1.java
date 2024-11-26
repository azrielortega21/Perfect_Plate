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

    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirm;
    private EditText etFullName;

    private String email;
    private String password;
    private String confirm;
    private String fullName;

    public static final String KEY_EMAIL = "KEY_EMAIL";
    public static final String KEY_FULLNAME = "KEY_FULLNAME";
    public static final String KEY_PASSWORD = "KEY_PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup1);

        btnNext = findViewById(R.id.signup1_btn_next);

        tvSignIn = findViewById(R.id.signup1_tv_signin);

        etEmail = findViewById(R.id.signup1_et_email);
        etPassword = findViewById(R.id.signup1_et_password);
        etConfirm = findViewById(R.id.signup1_et_confirm_password);

        etFullName = findViewById(R.id.signup1_et_fullName);

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString().trim();
                password = etPassword.getText().toString();
                confirm = etConfirm.getText().toString();
                fullName = etFullName.getText().toString().trim();

                Log.d("SIGN UP 1 - EMAIL", email);
                Log.d("SIGN UP 1 - PASSWORD", password);
                Log.d("SIGN UP 1 - CONFIRM", confirm);
                Log.d("SIGN UP 1 - NAME", fullName);

                if(isInfoValid()){
                    Intent i = new Intent(SignUpActivity1.this, SignUpActivity2.class);
                    i.putExtra(KEY_EMAIL, email);
                    i.putExtra(KEY_FULLNAME, fullName);
                    i.putExtra(KEY_PASSWORD, password);

                    SignUpActivity1.this.startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });
    }

    private boolean isInfoValid() {
        Boolean valid = true;

        if (password.length() < 6){
            showErrorMessage(etPassword, "Password should have at least 6 characters");
            valid = false;
        }

        if (!password.equals(confirm)){
            showErrorMessage(etConfirm, "Password should be equal");
            valid = false;
        }

        if (fullName.isEmpty()){
            showErrorMessage(etFullName, "Full name is Required");
            valid = false;
        }

        if (email.isEmpty()){
            showErrorMessage(etEmail, "Email is required");
            valid = false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            showErrorMessage(etEmail, "Email is invalid");
            valid = false;
        }

        return valid;
    }

    private void showErrorMessage(EditText inputBox, String error){
        inputBox.setError(error);
    }
}