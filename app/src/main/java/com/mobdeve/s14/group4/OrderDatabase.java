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
        Log.d("myTag", "Add Book Entered");
        String key = this.databaseReference.push().getKey();

        order.setId(key);

        this.databaseReference.child(key).setValue(order);

        if (DataHelper.user.isAdmin())
            DataHelper.addOrder(order);

        return key;
    }
}
