package com.mobdeve.s14.group4;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;

public class DataHelper {
    public static User user;
    public static Book selectedBook;

    public static ArrayList<Book> allBooks;
    public static ArrayList<Order> allOrders; //only for admin; garbage data for regular user

    public static UserDatabase userDatabase;
    public static BookDatabase bookDatabase;
    public static OrderDatabase orderDatabase;

    public static final String KEY_BOOK_ID = "KEY_BOOK_ID";
    public static final String KEY_CATEGORY = "KEY_CATEGORY";
    public static final String KEY_SEARCH = "KEY_SEARCH";

    public static void initDatabase(){
        allBooks = new ArrayList<Book>();
        allOrders = new ArrayList<Order>();

        userDatabase = new UserDatabase();
        bookDatabase = new BookDatabase();
        orderDatabase = new OrderDatabase();

        refreshBooks();
    }

    public static void refreshBooks(){
        initBooks(new CallbackListener() {
            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onFailure() {

            }
        });
    }

    public static void asyncRefreshBooks(Context context, final CallbackListener booksListener){
        Handler handler = new Handler(context.getMainLooper());

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                DataHelper.initBooks(booksListener);
            }
        };

        handler.post(runnable);
    }

    public static void refreshStock(String id, CallbackListener callbackListener){
        bookDatabase.getBook(id, new CallbackListener() {
            @Override
            public void onSuccess(Object o) {
                if (o != null){
                    Book b = (Book) o;
                    callbackListener.onSuccess(b.getStock());
                }
                else{
                    callbackListener.onSuccess(null);
                }
            }

            @Override
            public void onFailure() {
                callbackListener.onFailure();
            }
        });
    }

    public static void refreshOrders(){
        initOrders(new CallbackListener() {
            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onFailure() {

            }
        });
    }

    public static void asyncRefreshOrders(Context context, final CallbackListener ordersListener){
        Handler handler = new Handler(context.getMainLooper());

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                DataHelper.initOrders(ordersListener);
            }
        };

        handler.post(runnable);
    }

    //
    // USER
    //
    public static void loadUser(String uid, CallbackListener callbackListener){
        userDatabase.getUser(uid, new CallbackListener() {
            @Override
            public void onSuccess(Object o) {
                User user = (User) o;
                user.initCart();
                user.initializeOrderLists(new CallbackListener() {
                    @Override
                    public void onSuccess(Object o) {
                        setGlobalUser(user);

                        if (user.isAdmin())
                            refreshOrders();

                        callbackListener.onSuccess(true);
                    }

                    @Override
                    public void onFailure() {
                        setGlobalUser(user);
                        callbackListener.onSuccess(false);
                    }
                });
            }

            @Override
            public void onFailure() {
                Log.d("MISSING", "User" + uid + " not found");
                callbackListener.onFailure();
            }
        });
    }

    public static void setGlobalUser(User u){
        user = u;
    }

    //
    // BOOKS
    //
    public static void initBooks(final CallbackListener listener){
        bookDatabase.getAllBooks(new CallbackListener() {
            @Override
            public void onSuccess(Object o) {
                allBooks = (ArrayList<Book>) o;
                listener.onSuccess(allBooks);
            }

            @Override
            public void onFailure() {
                listener.onFailure();
                Log.d("FAILURE", "Failed to get books");
            }
        });
    }

    public static void addBook(Book book){
        allBooks.add(book);
    }

    public static void updateBook(Book book){
        for (Book b : allBooks){
            if (b.getId().equals(book.getId())){
                b = book;
            }
        }
    }

    public static void deleteBook(String id){
        ArrayList<Book> forDeletion = new ArrayList<>();

        for (Book b : allBooks){
            if (b.getId().equals(id))
                forDeletion.add(b);
        }

        for (Book b: forDeletion) {
            allBooks.remove(b);
        }
    }

    //
    // ORDERS
    //
    public static void initOrders(final CallbackListener listener){
        orderDatabase.getAllOrders(new CallbackListener() {
            @Override
            public void onSuccess(Object o) {
                allOrders = (ArrayList<Order>) o;
                listener.onSuccess(allOrders);
            }

            @Override
            public void onFailure() {
                listener.onFailure();
                Log.d("FAILURE", "Failed to get books");
            }
        });
    }

    public static void addOrder(Order order){
        allOrders.add(order);
    }
}
