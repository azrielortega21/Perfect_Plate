package com.mobdeve.s14.group4;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BookDatabase {
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    public BookDatabase(){
        this.auth = FirebaseAuth.getInstance();
        this.database = FirebaseDatabase.getInstance();
        this.databaseReference = this.database.getReference("books");
    }

    /**
     * For initializing DataHelper
     */
    public void getAllBooks(final CallbackListener callbackListener){
        ArrayList<Book> books = new ArrayList<Book>();

        this.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot bookSnapshot : snapshot.getChildren()){
                        Book book = bookSnapshot.getValue(Book.class);
                        books.add(book);
                    }
                }

                callbackListener.onSuccess(books);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                callbackListener.onFailure();
            }
        });
    }

    /**
     * Gets book if it exists
     * Returns null in callback listener if it does not exist
     * */
    public void getBook(String id, final CallbackListener callbackListener){
        this.databaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Book book = null;

                if (snapshot.exists()){
                    book = snapshot.getValue(Book.class);
                }

                callbackListener.onSuccess(book);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                callbackListener.onFailure();
            }
        });
    }

    /**
     * Find a single recipe from data helper
     * */
    public Book findBook(String id){
        for (Book book : DataHelper.allBooks){
            if(id.equals(book.getId())){
                return book;
            }
        }

        return null;
    }

//    /**
//     * Find recipes from data helper
//     * */
//    public ArrayList<Book> findBooks(ArrayList<String> bookIds){
//        ArrayList<String> clone = (ArrayList<String>) bookIds.clone();
//        ArrayList<Book> books = new ArrayList<Book>();
//
//        for (String id : clone){
//            Book book = findBook(id);
//
//            if (book != null){
//                books.add(book);
//            }
//            else{
//                bookIds.remove(id);
//            }
//        }
//
//        return books;
//    }

    public String addBook(Book book){
        Log.d("myTag", "Add Book Entered");
        String key = this.databaseReference.push().getKey();

        book.setId(key);

        this.databaseReference.child(key).setValue(book);

        return key;
    }

    /**
     * Delete book from books database.
     * */
    public void deleteBook(String id){
        this.databaseReference.child(id).setValue(null);
        DataHelper.deleteBook(id);
    }

    /**
    * for increasing stock
     * @param id String id of book
     * @param change int number to increase
     * @param callbackListener indicates whether succeeded or failed
    * */
    public void increaseStock(String id, int change, CallbackListener callbackListener){
        updateStock(id, change, true, callbackListener);
    }

    /**
     * for decreasing stock
     * @param id String id of book
     * @param change int number to decrease
     * @param callbackListener indicates whether succeeded or failed
     * */
    public void decreaseStock(String id, int change, CallbackListener callbackListener){
        updateStock(id, change, false, callbackListener);
    }

    /**
     * updates stock
     * */
    private void updateStock(String id, int change, boolean wasIncreased, CallbackListener callback){
        this.databaseReference.child(id).child("stock").runTransaction(new Transaction.Handler() {
            @NonNull
            @NotNull
            @Override
            public Transaction.Result doTransaction(@NonNull @NotNull MutableData currentData) {
                if (currentData.getValue() != null){
                    int stock;

                    if (wasIncreased){
                        stock = currentData.getValue(int.class) + change;
                    }
                    else{
                        stock = currentData.getValue(int.class) - change;

                        if (stock < 0){
                            return Transaction.abort();
                        }

                    }

                    currentData.setValue(stock);
                }

                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, boolean committed, @Nullable @org.jetbrains.annotations.Nullable DataSnapshot currentData) {
                if (committed){
                    callback.onSuccess(currentData);
                }
                else{
                    callback.onFailure();
                }
            }
        });
    }
}
