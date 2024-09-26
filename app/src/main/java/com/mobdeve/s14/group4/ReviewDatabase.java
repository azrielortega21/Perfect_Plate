package com.mobdeve.s14.group4;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ReviewDatabase {
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private UploadImage image;

    public ReviewDatabase(){
        this.auth = FirebaseAuth.getInstance();
        this.database = FirebaseDatabase.getInstance();
        this.databaseReference = this.database.getReference("reviews");
    }

//    public void

    public String addReview(Review review){
        String key = "empty";

        Book book = DataHelper.bookDatabase.findBook(review.getBookId());

        if (book != null){
            book.addRating(review.getRating());

            key = this.databaseReference.push().getKey();
            review.setId(key);

            this.databaseReference.child(key).setValue(review);
            DataHelper.allReviews.add(review);
        }

        return key;
    }

    /**
     * Get review from list
     * */
    public Review findReview(String reviewId){
        for (Review review : DataHelper.allReviews){
            if(reviewId.equals(review.getId())){
                return review;
            }
        }

        return null;
    }

    /**
     * Find ingredients from data helper
     * */
    public ArrayList<Review> findReviews(ArrayList<String> reviewIds){
        ArrayList<Review> reviews = new ArrayList<Review>();

        for (String id : reviewIds){
            reviews.add(findReview(id));
        }

        return reviews;
    }

    public void getAllReviews(final CallbackListener callbackListener){
        ArrayList<Review> reviews = new ArrayList<Review>();

        this.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot reviewSnapshot : snapshot.getChildren()){
                        Review review = reviewSnapshot.getValue(Review.class);

                        reviews.add(review);
                    }
                }
                Log.d("REVIEWDSIZE", String.valueOf(reviews.size()));
                callbackListener.onSuccess(reviews);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                callbackListener.onFailure();
            }
        });
    }

    public void deleteReview(Review review){
        int remove_index = -1;


        Book book = DataHelper.bookDatabase.findBook(review.getBookId());
        Log.d("recipe.getId()", book.getId());

        if (book != null) {
            book.removeRating(review.getRating());

            for (int i = 0; i < DataHelper.allReviews.size(); i++) {
                if (DataHelper.allReviews.get(i).getId().equals(review.getId())) {
                    remove_index = i;
                    break;
                }
            }

            DataHelper.allReviews.remove(remove_index);
            this.databaseReference.child(review.getId()).setValue(null);
        }
    }
}
