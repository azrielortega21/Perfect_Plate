package com.mobdeve.s14.group4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Arrays;

public class EditProfileActivity extends AppCompatActivity {

    private Button btnEdit;

    private ImageView ivPic;

    private EditText etName;
//    private EditText etUsername;
    private EditText etEmail;

    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private ImageView ivEmail;

    private ProgressBar pb;

    private Uri imageUri;

    private StorageReference storageRef;

    public static final int IMAGE_GALLERY_REQUEST_EP = 21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        storageRef = FirebaseStorage.getInstance().getReference("profile_pics");

        this.initComponents();
    }

    private void initComponents() {

        pb = findViewById(R.id.editprofile_pb);
        ivPic = findViewById(R.id.editprofile_iv_profile_pic);

        etName = findViewById(R.id.editprofile_et_name);
//        etUsername = findViewById(R.id.editprofile_et_username);
        etEmail = findViewById(R.id.editprofile_et_email);
        ivEmail = findViewById(R.id.editprofile_iv_email);

        if(DataHelper.user.getFirebaseUser().getProfile_Image() != null){
            Log.d("userPic", DataHelper.user.getFirebaseUser().getProfile_Image().getmImageUrl());
            Picasso.with(EditProfileActivity.this)
                    .load(DataHelper.user.getFirebaseUser().getProfile_Image().getmImageUrl())
                    .placeholder(R.drawable.vectorperson)
                    .fit()
                    .centerCrop()
                    .into(ivPic);
        }
        else{
            ivPic.setImageResource(R.drawable.vectorperson);
        }

        ivPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (Intent.ACTION_PICK);

                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String path = pictureDirectory.getPath();

                imageUri = Uri.parse(path);

                i.setDataAndType(imageUri, "image/*");

                startActivityForResult(i, IMAGE_GALLERY_REQUEST_EP);
            }
        });


        btnEdit = findViewById(R.id.editprofile_btn_edit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEdit.setEnabled(false);
                pb.setVisibility(View.VISIBLE);
//                TODO: validate updated data
//                if(TextUtils.isEmpty(etName.getText()) ||
//                        TextUtils.isEmpty(etUsername.getText())){
//
//                    if(TextUtils.isEmpty(etName.getText()))
//                        etName.setError("Name is Required");
//
//
//                    if(TextUtils.isEmpty(etUsername.getText()))
//                        etUsername.setError("Username is Required");
//
//
//                    if(TextUtils.isEmpty(etEmail.getText()))
//                        etEmail.setError("Email is Required");
//
//                    pb.setVisibility(View.GONE);
//                    btnEdit.setEnabled(true);
//                    Toast.makeText(EditProfileActivity.this, "Fill Up All Values", Toast.LENGTH_LONG).show();
//                }
//                else{
//
//                    User newUser = new User();
//
//                    String fname, username, email;
//                    String lname = "";
//                    String [] fullName = etName.getText().toString().trim().split(" ");
//
//                    fname = fullName[0];
//
//                    //combine remaining name to last name
//                    for (int i = 1; i < fullName.length; i++){
//                        lname = lname.concat(fullName[i] + " ");
//                    }
//
//                    if (etEmail.isShown()){
//                        email = etEmail.getText().toString().trim();
//                        newUser.setEmail(email);
//                    }
//                    username = etUsername.getText().toString().trim();
//
//                    newUser.setUsername(username);
//                    newUser.setFirstName(fname);
//                    newUser.setLastName(lname);
//
//                    uploadFile(newUser);
//                }
            }
        });

        loadInfo();
    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadFile(User user){
        if (imageUri != null){
            final StorageReference fileRef = storageRef.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            UploadTask uploadTask = fileRef.putFile(imageUri);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String downloadURL = downloadUri.toString();

                        UploadImage upload = new UploadImage ("profilePic", downloadURL);
                        user.setProfile_Image(upload);

                        //upload everything to DB;
                        new UserDatabase().updateCurrentUser(user);
                        Log.d("getImageUrl", user.getFirebaseUser().getProfile_Image().getmImageUrl());

                        Toast.makeText(EditProfileActivity.this, "Updated User Successfully!", Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.GONE);
                        btnEdit.setEnabled(true);
                        finish();
                    } else {
                        // Handle failures
                        // ...
                        pb.setVisibility(View.GONE);
                        btnEdit.setEnabled(true);
                        Toast.makeText(EditProfileActivity.this, "FAIL ADDING PHOTO", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            new UserDatabase().updateCurrentUser(user);
            Toast.makeText(EditProfileActivity.this, "Updated User Successfully!", Toast.LENGTH_SHORT).show();
            pb.setVisibility(View.GONE);
            btnEdit.setEnabled(true);
            finish();
        }

    }

    private void loadInfo() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("PROFILE SIGNED IN", "onAuthStateChanged:signed_in:" + user.getUid());
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                TODO: add on data change for user
//                String fname, lname, username, email;
//                fname = dataSnapshot.child("firstName").getValue(String.class);
//                lname = dataSnapshot.child("lastName").getValue(String.class);
//                username = dataSnapshot.child("username").getValue(String.class);
//                email = dataSnapshot.child("email").getValue(String.class);
//
//                if (email == null){
//                    etEmail.setVisibility(View.GONE);
//                    ivEmail.setVisibility(View.GONE);
//                }
//                else{
//                    etEmail.setText(email);
//                }
//
//                String fullName = fname + " " + lname;
//                etName.setText(fullName);
//                etUsername.setText(username);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("FAIL TAG", "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == IMAGE_GALLERY_REQUEST_EP){
                imageUri = data.getData();
                Picasso.with(this).load(imageUri).into(ivPic);
            }
            else{
                finish();
            }
        }
        else {
            finish();
        }
    }
}