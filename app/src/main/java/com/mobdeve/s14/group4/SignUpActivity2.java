package com.mobdeve.s14.group4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity2 extends AppCompatActivity {
    private Button btnSignUp;
    private String email;
    private String password;
    private String fullName;

    private TextView tvSignIn;

    private EditText etStreetAddress;
    private EditText etCity;
    private EditText etState;
    private EditText etPostalCode;
    private Address address;

    private ProgressBar pbSignup;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    private int year = 2010;
    private int month = 1;
    private int day = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.initFirebase();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        this.btnSignUp = findViewById(R.id.signup2_btn_signup);

        this.etStreetAddress = findViewById(R.id.signup2_et_sAddress);
        this.etCity = findViewById(R.id.signup2_et_city);
        this.etState = findViewById(R.id.signup2_et_state);
        this.etPostalCode = findViewById(R.id.signup2_etPostalCode);

        this.tvSignIn = findViewById(R.id.signup2_tv_signin);

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (SignUpActivity2.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        this.pbSignup = findViewById(R.id.pb_signup);

        Intent i = getIntent();

        email = i.getStringExtra(SignUpActivity1.KEY_EMAIL);
        password = i.getStringExtra(SignUpActivity1.KEY_PASSWORD);
        fullName = i.getStringExtra(SignUpActivity1.KEY_FULLNAME);

        Log.d("TEST EMAIL SIGN UP 2", email);
        Log.d("TEST PASSWORD SIGN UP 2", password);
        Log.d("TEST USERNAME SIGN UP 2", fullName);

        this.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String street = etStreetAddress.getText().toString().trim();
                String city = etCity.getText().toString().trim();
                String state = etState.getText().toString().trim();
                String postalCode = etPostalCode.getText().toString().trim();

                address = new Address(street, city, state, postalCode);

                User user = new User(fullName, email, password, address);
                if (isValidInfo()){
                    storeUser(user);
                }
            }
        });

    }

    private boolean isValidInfo(){
        boolean valid = true;

        if (address.getStreet().isEmpty()){
            showError(etStreetAddress, "Field is Required");
            valid = false;
        }

        if (address.getCity().isEmpty()){
            showError(etCity, "Field is Requried");
            valid = false;
        }

        if (address.getState().isEmpty()){
            showError(etState, "Field is Required");
            valid = false;
        }

        if (address.getPostalCode().isEmpty()){
            showError(etPostalCode, "Field is Required");
            valid = false;
        }

        return valid;
    }

    private void showError(EditText inputBox, String error){
        inputBox.setError(error);
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void storeUser(User u){
        this.btnSignUp.setVisibility(View.VISIBLE);

        this.pbSignup.setVisibility(View.VISIBLE);
        Log.d("TEST EMAIL STORE", email);
        Log.d("TEST PASSWORD STORE", password);
        Log.d("TEST USERNAME STORE", fullName);

        Log.d("TEST GET EMAIL STORE", u.getEmail());
        Log.d("TEST GET EMAIL PASSWORD", u.getPassword());

//        register to firebase
        this.mAuth.createUserWithEmailAndPassword(u.getEmail(), u.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            u.setUserId(mAuth.getCurrentUser().getUid());

                            new UserDatabase().addUser(u);

                            successfulRegistration(u);
                        } else {
                            failedRegistration();
                        }
                    }
                });
    }

    private void successfulRegistration(User user){
        Toast.makeText(this, "User Registration Successful", Toast.LENGTH_SHORT).show();

        this.btnSignUp.setVisibility(View.GONE);

        DataHelper.setGlobalUser(user);

        moveToSearchActivity();
        finish();
    }

    private void failedRegistration(){
        this.btnSignUp.setVisibility(View.GONE);
        Toast.makeText(this, "SIGN UP FAIL", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(SignUpActivity2.this, MainActivity.class);
        startActivity(i);

        finish();
    }

    private void initFirebase() {
        this.mAuth = FirebaseAuth.getInstance();
        this.mDatabase = FirebaseDatabase.getInstance();
    }

    private void moveToSearchActivity(){
        Intent i = new Intent(SignUpActivity2.this, SearchActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}