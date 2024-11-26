package com.mobdeve.s14.group4;

public class Book {
    private String bookId;

    private int bookPic;
    private UploadImage image;

    private String bookName;

    private String author;
    private String category;

    private double price;
    private int stock;

    public Book(){

    }

    //get from database
    public Book(String id, int bookPic, UploadImage upload, String bookName,
                String author, String category, double price, int stock){
        this.bookId = id;

        this.bookPic = bookPic;
        this.image = upload;

        this.bookName = bookName;

        this.author = author;
        this.category = category;

        this.price = price;
        this.stock = stock;
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

    public String getAuthor() { return this.author; }

    public String getCategory () {return this.category;}

    public double getPrice() { return this.price; }

    public int getStock() { return this.stock; }

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

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public void setPrice(double price) { this.price = price; }

    public void setStock(int stock) { this.stock = stock; }
}
