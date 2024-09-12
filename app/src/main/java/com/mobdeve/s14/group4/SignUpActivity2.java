package com.mobdeve.s14.group4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity2 extends AppCompatActivity {
    private Button btnSignUp;
    private String email;
    private String password;
    private String username;

    private String fullName;

    private EditText etStreetAddress;
    private EditText etCity;
    private EditText etState;
    private EditText etPostalCode;


    private ProgressBar pbSignup;
    private FirebaseAuth mAuth;
    //private FirebaseDatabase mDatabase;

    private int year = 2010;
    private int month = 1;
    private int day = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this.initFirebase();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        this.btnSignUp = findViewById(R.id.signup2_btn_signup);

        this.etStreetAddress = findViewById(R.id.signup2_et_sAddress);
        this.etCity = findViewById(R.id.signup2_et_city);
        this.etState = findViewById(R.id.signup2_et_state);
        this.etPostalCode = findViewById(R.id.signup2_et_state);

        this.pbSignup = findViewById(R.id.pb_signup);

        Intent i = getIntent();

        email = i.getStringExtra(SignUpActivity1.KEY_EMAIL);
        password = i.getStringExtra(SignUpActivity1.KEY_PASSWORD);
        username = i.getStringExtra(SignUpActivity1.KEY_FULLNAME);

        Log.d("TEST EMAIL SIGN UP 2", email);
        Log.d("TEST PASSWORD SIGN UP 2", password);
        Log.d("TEST USERNAME SIGN UP 2", username);

//        etBirthday.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatePickerDialog datePicker = new DatePickerDialog(SignUpActivity2.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int day) {
//                        month = month + 1;
//                        String date = day +"/" + month + "/" + year;
//                        etBirthday.setText(date);
//                    }
//                }, year, month, day);
//                datePicker.show();
//            }
//        });

//        this.btnSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                fullName = et.getText().toString().trim();
//                lname = etLastN.getText().toString().trim();
//                String birthday = etBirthday.getText().toString().trim();
//
//                //Toast.makeText(SignUpActivity2.this, birthday, Toast.LENGTH_SHORT).show();
//
//                User user = new User(email, password, username, fname, lname, birthday);
//                if (validateUser(user)){
//                    //add user to db
//
//                    storeUser(user);
//                }
//            }
//        });
//
//    }

//    private boolean validateUser(User user){
//        boolean isValidUser = true;
//
//        //validate email
//        if(user.getEmail().isEmpty()){
//            isValidUser = false;
//        }
//
//        //validate password
//        if (user.getPassword().isEmpty()){
//            isValidUser = false;
//        }
//
//        //validate username
//        if (user.getUsername().isEmpty()){
//            isValidUser = false;
//        }
//
//        //validate first name
//        if (user.getFirstName().isEmpty()){
//            isValidUser = false;
//        }
//
//        //validate last name
//        if (user.getLastName().isEmpty()){
//            isValidUser = false;
//        }
//
//        if (user.getBirthday().isEmpty()){
//            isValidUser = false;
//        }
//
//        return isValidUser;
//    }
//
//
//    @Override
//    public void finish(){
//        super.finish();
//        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
//    }
//
//    private void storeUser(User u){
//        this.btnSignUp.setVisibility(View.VISIBLE);
//
//        this.pbSignup.setVisibility(View.VISIBLE);
//        Log.d("TEST EMAIL STORE", email);
//        Log.d("TEST PASSWORD STORE", password);
//        Log.d("TEST USERNAME STORE", username);
//        Log.d("TEST FNAME STORE", fname);
//        Log.d("TEST LNAME STORE", lname);
//
//        Log.d("TEST GETEMAIL STORE", u.getEmail());
//        Log.d("TEST GETEMAIL PASSWORD", u.getPassword());

        //register to firebase
//        this.mAuth.createUserWithEmailAndPassword(u.getEmail(), u.getPassword())
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            u.setUserId(mAuth.getCurrentUser().getUid());
//
//                            new UserDatabase().addUser(u);
//
//                            successfulRegistration(u);
//                        } else {
//                            failedRegistration();
//                        }
//                    }
//                });
    }

    private void successfulRegistration(User user){
        Toast.makeText(this, "User Registration Successful", Toast.LENGTH_SHORT).show();

        this.btnSignUp.setVisibility(View.GONE);

        DataHelper.setGlobalUser(user);

        moveToHomeActivity();
        finish();
    }

    private void failedRegistration(){
        this.btnSignUp.setVisibility(View.GONE);
        Toast.makeText(this, "SIGN UP FAIL", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(SignUpActivity2.this, MainActivity.class);
        startActivity(i);

        finish();
    }

//    private void initFirebase() {
//        this.mAuth = FirebaseAuth.getInstance();
//        this.mDatabase = FirebaseDatabase.getInstance();
//    }

    private void moveToHomeActivity(){
        Intent i = new Intent(SignUpActivity2.this, HomeActivity.class);
        startActivity(i);
    }
}