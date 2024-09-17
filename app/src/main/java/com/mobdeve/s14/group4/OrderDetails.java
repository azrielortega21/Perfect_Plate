package com.mobdeve.s14.group4;

import com.google.firebase.database.Exclude;

public class OrderDetails {
    //
    // ADDED TO FIREBASE
    //
    private String bookId;
    private int quantity;

    //
    // EXCLUDED FROM FIREBASE
    //
    private Book book;

    public OrderDetails(){

    }

    public OrderDetails(String bookId, int quantity){
        this.bookId = bookId;
        this.quantity = quantity;
    }

    public OrderDetails(String bookId, int quantity, Book book){
        this.bookId = bookId;
        this.quantity = quantity;
        this.book = book;
    }

    public String getBookId() {
        return bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    //
    // EXCLUDED FROM FIREBASE
    //
    @Exclude
    public Book getBook() {
        return book;
    }

    @Exclude
    public void setBook(Book book) {
        this.book = book;
    }

    @Exclude
    public void addOne(){
        this.quantity += 1;
    }

    @Exclude
    public void subOne(){
        if (this.quantity > 0)
            this.quantity -= 1;
    }
}
