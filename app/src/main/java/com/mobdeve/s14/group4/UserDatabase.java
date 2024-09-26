package com.mobdeve.s14.group4;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UserDatabase {
    private final DatabaseReference databaseReference;

    public UserDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        this.databaseReference = database.getReference("users");
    }

    /**
     * Gets user details from database. Returns details in a callbacklistener.
     * */
    public void getFirebaseUser(String userId, final CallbackListener listener){
        this.databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    listener.onSuccess(user);
                } else {
                    listener.onFailure();
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {
                Log.w("FAIL TAG", "User not found.", error.toException());
            }
        });
    }

    public void getUser(String userId, final CallbackListener listener){
        getFirebaseUser(userId, new CallbackListener() {
            @Override
            public void onSuccess(Object o) {
                listener.onSuccess((User) o);
            }

            @Override
            public void onFailure() {
                listener.onFailure();
            }
        });
    }

    public void addUser(User user){
        this.databaseReference.child(user.getUserId()).setValue(user);
    }

    /**
     * Updates user values based on non-null and non-empty variables
     * Does not update books or orders
     *
     * @param newUser  new details to be updated
     * */
    public void updateCurrentUser(User newUser){
        Log.d("editprofiletag", "UPDATING CURRENT USER");
        User user = DataHelper.user;

        if(newUser.getFullName() != null) {
            String fullName = newUser.getFullName().trim();
            if (!fullName.isEmpty()){
                user.setFullName(fullName);
            }
        }

        if(newUser.getEmail() != null) {
            String email = newUser.getEmail().trim();
            if (!email.isEmpty()) {
                user.setEmail(email);
            }
        }

        if(newUser.getAddress() != null){
            Address address = newUser.getAddress();
            if(address.isValid()){
                user.setAddress(address);
            }
        }

        databaseReference.child(user.getUserId()).setValue(user);
    }

    /**
     * Adds user order under user. Adds order id to user's list oin the database
     * Adds order to the database.
     *
     * @param order    recipe to be added to the database
     * */
    public void addUserOrder(Order order){
        User user = DataHelper.user;

        String orderId = DataHelper.orderDatabase.addOrder(order);
        order.setId(orderId);

        user.addOrder(order);
        updateUserOrders(user.getUserId(), user.getUserOrdersList());
    }

//    /**
//     * Removes user order from user's list and from the order db
//     * */
//    public void updateUserOrder(String orderId){
//        //remove from recipe db
//        DataHelper.orderDatabase.cancelOrder(orderId);
//
//        //remove from user db
//        User user = DataHelper.user;
//        user.removeUserRecipe(recipeId);
//        updateUserRecipes(user.getUserId(), user.getUserOrdersList(), user.getUserRecipesCount());
//    }

    public void updateUserOrders(String userId, ArrayList<String> orderList){
        this.databaseReference.child(userId)
                .child("userOrdersList")
                .setValue(orderList);
    }
}
