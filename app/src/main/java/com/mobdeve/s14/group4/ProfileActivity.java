package com.mobdeve.s14.group4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private RecyclerView rvBooks;
    private RecyclerView.LayoutManager manager;
    private ProfileAdapter adapter;

    private ImageView ivEdit;
    private ImageView ivProfilePic;
    private TextView tvName;
    private TextView tvUsername;
    private TextView tvBooks;

    private FirebaseUser user;
    //private DatabaseReference databaseReference;

    private ImageButton ibBack;
    private ImageButton ibAdd;
    private Button btnLogout;

    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.initRecyclerView();
        this.initComponents();
    }

    private void signOut(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mGoogleSignInClient.signOut();
        FirebaseAuth.getInstance().signOut();
    }

    private void initComponents(){
        this.ivEdit = findViewById(R.id.profile_iv_edit);
        this.tvName = findViewById(R.id.profile_tv_name);
        this.tvUsername = findViewById(R.id.profile_tv_username);
        this.tvBooks = findViewById(R.id.profile_tv_nobooks);
        this.ibBack = findViewById(R.id.ib_profile_back);
        this.ibAdd = findViewById(R.id.ib_profile_add);
        this.btnLogout = findViewById(R.id.btn_profile_logout);
        this.ivProfilePic = findViewById(R.id.profile_iv_profile_pic);

        User user = DataHelper.user;

        if(user.getFirebaseUser().getProfile_Image() != null){
            Log.d("userPic", user.getFirebaseUser().getProfile_Image().getmImageUrl());
            Picasso.with(ProfileActivity.this)
                    .load(user.getFirebaseUser().getProfile_Image().getmImageUrl())
                    .placeholder(R.drawable.vectorperson)
                    .fit()
                    .centerCrop()
                    .into(ivProfilePic);
        }
        else{
            ivProfilePic.setImageResource(R.drawable.vectorperson);
        }


        tvName.setText(DataHelper.user.getFullName());
        tvUsername.setText(DataHelper.user.getUsername());
//        String recipeCount = "Recipes: " + DataHelper.user.getUserRecipesCount();
//        tvRecipes.setText(recipeCount);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,
                        LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
//                Intent i = new Intent(ProfileActivity.this, CreateRecipeActivity1.class);
//                startActivityForResult(i, 1);
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                signOut();
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                ProfileActivity.this.startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        rvBooks = findViewById(R.id.profile_rv_books);

        manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvBooks.setLayoutManager(manager);

//        adapter = new ProfileAdapter(DataHelper.user.getUserRecipes());
        rvBooks.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.recreate();
    }
}