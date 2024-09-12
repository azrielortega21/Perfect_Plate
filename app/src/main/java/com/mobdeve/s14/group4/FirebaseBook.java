package com.mobdeve.s14.group4;

import java.util.ArrayList;

public class FirebaseBook {
    private String bookId;

    private int bookPic;
    private UploadImage image;

    private String bookName;
    private String description;

    private String author;

    private double price;
    private int stock;

    private int faveCount;
    private double rating;
    private int reviewCount;

    private String category;

    public FirebaseBook(){

    }

    //get from database
    public FirebaseBook(String id, int bookPic, UploadImage upload, String bookName, String desc,
                        String author, double price, int stock, int faveCount, double rating,
                        int reviewCount, String category){
        this.bookId = id;

        this.bookPic = bookPic;
        this.image = upload;

        this.bookName = bookName;
        this.description = desc;

        this.author = author;

        this.price = price;
        this.stock = stock;

        this.faveCount = faveCount;
        this.rating = rating;
        this.reviewCount = reviewCount;

        this.category = category;
    }

    public String getId(){
        return this.bookId;
    }

    public int getBookPic(){
        return this.bookPic;
    }

    public UploadImage getUploadImage (){
        return this.image;
    }

    public String getBookName(){
        return this.bookName;
    }

    public String getDescription(){
        return this.description;
    }

    public String getAuthor() { return this.author; }

    public double getPrice() { return this.price; }

    public int getStock() { return this.stock; }

    public int getFaveCount(){
        return this.faveCount;
    }

    public double getRating(){
        return this.rating;
    }

    public int getReviewCount(){
        return this.reviewCount;
    }

    public String getCategory () {return this.category;}



    public void setId(String id){
        this.bookId = id;
    }

    public void setBookPic(int pic){
        this.bookPic = pic;
    }

    public void setUploadImage (UploadImage ui){
        this.image = ui;
    }

    public void setBookName(String bookName){
        this.bookName = bookName;
    }

    public void setDescription(String description) { this.description = description; }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(double price) { this.price = price; }

    public void setStock(int stock) { this.stock = stock; }

    public void setFaveCount(int faveCount){
        this.faveCount = faveCount;
    }

    public void setRating(double rating){
        this.rating = rating;
    }

    public void setReviewCount(int reviewCount){
        this.reviewCount = reviewCount;
    }

    public void setCategory(String category){
        this.category = category;
    }
}
