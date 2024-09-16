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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderDatabase {
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    public OrderDatabase(){
        this.auth = FirebaseAuth.getInstance();
        this.database = FirebaseDatabase.getInstance();
        this.databaseReference = this.database.getReference("orders");
    }

    /**
     * For initializing DataHelper
     */
    public void getAllOrders(final CallbackListener callbackListener){
        ArrayList<Order> orders = new ArrayList<Order>();

        this.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot recipeSnapshot : snapshot.getChildren()){
                        Order order = recipeSnapshot.getValue(Order.class);
                        orders.add(order);
                    }
                }

                callbackListener.onSuccess(orders);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                callbackListener.onFailure();
            }
        });
    }

    /**
     * For initializing DataHelper
     */
    public void getOrders(ArrayList<String> ids, final CallbackListener callbackListener){
        ArrayList<Order> orders = new ArrayList<Order>();

        this.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot recipeSnapshot : snapshot.getChildren()){
                        Order order = recipeSnapshot.getValue(Order.class);
                        if (ids.contains(order.getId()))
                            orders.add(order);
                    }
                }

                callbackListener.onSuccess(orders);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                callbackListener.onFailure();
            }
        });
    }

    public String addOrder(Order order){
        Log.d("myTag", "Add Order Entered");
        String key = this.databaseReference.push().getKey();

        order.setId(key);

        this.databaseReference.child(key).setValue(order);

        if (DataHelper.user.isAdmin())
            DataHelper.addOrder(order);

        return key;
    }

    public void placeOrder(Order order, CallbackListener callbackListener){
        ArrayList<OrderDetails> success = new ArrayList<OrderDetails>();
        ArrayList<OrderDetails> failed = new ArrayList<OrderDetails>();

        // async running
        new Thread(() -> {
            try {
                int size = order.getOrderDetails().size();
                ExecutorService ORDER_THREAD_POOL = Executors.newFixedThreadPool(size);
                CountDownLatch latch = new CountDownLatch(size);

                for (OrderDetails od : order.getOrderDetails()){
                    ORDER_THREAD_POOL.submit(() -> {
                        DataHelper.bookDatabase.decreaseStock(od.getBookId(), od.getQuantity(), new CallbackListener() {
                            @Override
                            public void onSuccess(Object o) {
                                success.add(od);
                                latch.countDown();
                            }

                            @Override
                            public void onFailure() {
                                failed.add(od);
                                latch.countDown();
                            }
                        });
                    });
                }

                latch.await();

                // if there are some successful, create order and add to db and user
                if (success.size() > 0){
                    Order newOrder = new Order(order.getCustomer(), order.getAddress());
                    newOrder.setOrderDetails(success);

                    if (order.getModeOfPay().equals("COD")){
                        newOrder.setCOD();
                    }
                    else{
                        newOrder.setGCash();
                    }

                    DataHelper.userDatabase.addUserOrder(newOrder);
                }

                callbackListener.onSuccess(failed);
            }
            catch(InterruptedException e){
                callbackListener.onFailure();
            }
        }).start();
    }
}
