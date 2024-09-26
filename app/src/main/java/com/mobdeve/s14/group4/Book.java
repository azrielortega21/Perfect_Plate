package com.mobdeve.s14.group4;

import android.util.Log;

import java.text.DecimalFormat;

public class Book extends FirebaseBook
{
    public Book() {
        super();
    }

    public Book(FirebaseBook firebaseBook){
        setId(firebaseBook.getId());

        setBookPic(firebaseBook.getBookPic());
        setUploadImage(firebaseBook.getUploadImage());

        setBookName(firebaseBook.getBookName());
        setDescription(firebaseBook.getDescription());

        setAuthor(firebaseBook.getAuthor());

        setPrice(firebaseBook.getPrice());
        setStock(firebaseBook.getStock());

        setFaveCount(firebaseBook.getFaveCount());
        setRating(firebaseBook.getRating());
        setReviewCount(firebaseBook.getReviewCount());

        setCategory(firebaseBook.getCategory());
    }

    //with id from database
    public Book(String id, int bookPic, UploadImage upload, String bookName, String desc,
                String author, double price, int stock, int faveCount, double rating,
                int reviewCount, String category){
        super(id, bookPic, upload, bookName, desc, author, price, stock, faveCount, rating, reviewCount, category);
    }

    public String getRatingString() {
        if (getRating() > 0){
            DecimalFormat value = new DecimalFormat("#.#");
            return value.format(getRating());
        }
        return "0.0";
    }

    public FirebaseBook getFirebaseBook(){
        FirebaseBook firebaseBook = new FirebaseBook();

        firebaseBook.setId(getId());

        firebaseBook.setBookPic(getBookPic());
        firebaseBook.setUploadImage(getUploadImage());

        firebaseBook.setBookName(getBookName());
        firebaseBook.setDescription(getDescription());

        firebaseBook.setAuthor(getAuthor());

        firebaseBook.setPrice(getPrice());
        firebaseBook.setStock((getStock()));

        firebaseBook.setFaveCount(getFaveCount());
        firebaseBook.setRating(getRating());
        firebaseBook.setReviewCount(getReviewCount());

        firebaseBook.setCategory(getCategory());

        Log.d("myTag", String.valueOf(firebaseBook));

        return firebaseBook;
    }

    public void addRating(double newRating){
        int count = getReviewCount();
        int newCount = count + 1;

        setReviewCount(newCount);
        DataHelper.bookDatabase.updateReviewCount(getId(), newCount);

        double rating = getRating();
        rating = (rating * count / newCount) + (newRating / newCount);

        //update lists
        setRating(rating);
        DataHelper.bookDatabase.updateRating(getId(), rating);
    }

    public void removeRating(double removeRating){
        int count = getReviewCount();
        int newCount = count - 1;

        setReviewCount(newCount);
        DataHelper.bookDatabase.updateReviewCount(getId(), newCount);

        if (newCount > 0){
            double rating = getRating();
            rating = (rating * count / newCount) - (removeRating / newCount);

            if (rating < 0)
                rating = 0;

            //update lists
            setRating(rating);
            DataHelper.bookDatabase.updateRating(getId(), rating);
        }
        else{
            setRating(0);
            DataHelper.bookDatabase.updateRating(getId(), 0);
        }
    }
}