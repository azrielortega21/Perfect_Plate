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
                    for (DataSnapshot recipeSnapshot : snapshot.getChildren()){
                        Book book = recipeSnapshot.getValue(Book.class);
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

    public void updateBook(Book book){
        Log.d("myTag", "Update Book");

        this.databaseReference.child(book.getId()).setValue(book);
        DataHelper.updateBook(book);
    }

    /**
     * Delete book from books database.
     * */
    public void deleteBook(String id){
        this.databaseReference.child(id).setValue(null);
        DataHelper.deleteBook(id);
    }
}
