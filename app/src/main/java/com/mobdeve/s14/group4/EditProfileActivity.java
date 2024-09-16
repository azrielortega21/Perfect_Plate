package com.mobdeve.s14.group4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etPhone;
    private EditText etStreet;
    private EditText etCity;
    private EditText etProvince;
    private EditText etPostal;

    private String phone;
    private String street;
    private String city;
    private String province;
    private String postal;

    private Button btnCancel;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initComponents();
    }

    private void initComponents() {
        this.etPhone = findViewById(R.id.et_edit_phone);
        this.etStreet = findViewById(R.id.et_edit_address);
        this.etCity = findViewById(R.id.et_edit_city);
        this.etProvince = findViewById(R.id.et_edit_province);
        this.etPostal = findViewById(R.id.et_edit_postal);

        this.btnCancel = findViewById(R.id.btn_edit_cancel);
        this.btnSave = findViewById(R.id.btn_edit_save);

        etPhone.setText(DataHelper.user.getContactNo());
        etStreet.setText(DataHelper.user.getAddress().getStreet());
        etCity.setText(DataHelper.user.getAddress().getCity());
        etProvince.setText(DataHelper.user.getAddress().getState());
        etPostal.setText(DataHelper.user.getAddress().getPostalCode());


        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSave.setEnabled(false);

                //insert Validate Info

                phone = etPhone.getText().toString().trim();
                street = etStreet.getText().toString().trim();
                city = etCity.getText().toString().trim();
                province= etProvince.getText().toString().trim();
                postal = etPostal.getText().toString().trim();

                Address address = new Address(street, city, province, postal);
                if(isValidInfo(address, phone)) {
                    updateUser(address, phone);
                    finish();
                }
                btnSave.setEnabled(true);
            }
        });
    }

    private boolean isValidInfo(Address address, String contactNo) {
        boolean valid = true;

        if (address.getStreet().isEmpty()){
            showError(etStreet, "Field is Required");
            valid = false;
        }

        if (address.getCity().isEmpty()){
            showError(etCity, "Field is Required");
            valid = false;
        }

        if (address.getState().isEmpty()){
            showError(etProvince, "Field is Required");
            valid = false;
        }

        if (address.getPostalCode().isEmpty()){
            showError(etPostal, "Field is Required");
            valid = false;
        }

        if(contactNo.isEmpty()){
            showError(etPhone, "Field is Required");
            valid = false;
        }

        if(contactNo.length() != 11){
            showError(etPhone, "Invalid Phone Number");
            valid = false;
        }

        if(!contactNo.substring(0, 2).equals("09")){
            showError(etPhone, "Phone Number should start with 09");
            valid = false;
        }

        return valid;
    }

    private void updateUser(Address address, String phone) {

        User user = DataHelper.user;
        user.setAddress(address);
        user.setContactNo(phone);

        new UserDatabase().updateUser(user);
    }

    private void showError(EditText inputBox, String error){
        inputBox.setError(error);
    }
}