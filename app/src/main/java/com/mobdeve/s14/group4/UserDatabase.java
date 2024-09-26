package com.mobdeve.s14.group4;

import android.util.Log;

import androidx.annotation.NonNull;

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
                    FirebaseUser firebaseUser = dataSnapshot.getValue(FirebaseUser.class);
                    listener.onSuccess(firebaseUser);
                } else {
                    listener.onFailure();
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {
                // Failed to read value
                Log.w("FAIL TAG", "User not found.", error.toException());
            }
        });
    }

    public void getUser(String userId, final CallbackListener listener){
        getFirebaseUser(userId, new CallbackListener() {
            @Override
            public void onSuccess(Object o) {
                FirebaseUser firebaseUser = (FirebaseUser) o;
                User user = new User(firebaseUser);
                listener.onSuccess(user);
            }

            @Override
            public void onFailure() {
                listener.onFailure();
            }
        });
    }

    public void addUser(User user){
        this.databaseReference.child(user.getUserId()).setValue(user.getFirebaseUser());
    }

    /**
     * For initializing DataHelper
     */
    public void getAllUsers(final CallbackListener callbackListener){
        ArrayList<User> users = new ArrayList<User>();

        this.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot userSnapshot : snapshot.getChildren()){
                        FirebaseUser firebaseUser = userSnapshot.getValue(FirebaseUser.class);
                        User user = new User(firebaseUser);

                        users.add(user);
                    }
                }

                callbackListener.onSuccess(users);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                callbackListener.onFailure();
            }
        });
    }

    public User findUser(String userId){
        for (User user : DataHelper.allUsers){
            if (user.getUserId().equals(userId)){
                return user;
            }
        }

        return null;
    }

    /**
     * Updates user values based on non-null and non-empty variables
     * Does not update recipe or ingredient details
     *
     * @param newUser  new details to be updated
     * */
    public void updateCurrentUser(User newUser){

        Log.d("editprofiletag", "UPDATING CURRENT USER");
        User user = DataHelper.user;

        //User user = new User();


        String fullName = newUser.getFullName().trim();
        if (!fullName.isEmpty()){
            user.setFullName(fullName);
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

        if(newUser.getProfile_Image() != null) {
            UploadImage profile_pic = newUser.getProfile_Image();
            if (profile_pic != null) {
                user.setProfile_Image(profile_pic);
            }
        }

        //TODO: set birthday
        databaseReference.child(user.getUserId()).setValue(user.getFirebaseUser());
    }
//
//    /**
//     * Adds user recipe under user. Assigns userId as contributorId of recipe and adds recipe Id
//     * to the user's list of recipes in the database. Adds recipe to the database.
//     *
//     * @param book    recipe to be added to the database
//     * */
//    public void addUserRecipe(Book book){
//        User user = DataHelper.user;
//        book.setContributor(user);
//
//        String recipeId = DataHelper.bookDatabase.addBook(book);
//        book.setId(recipeId);
//
//        user.addUserRecipe(book);
//        DataHelper.addBook(book);
//        updateUserRecipes(user.getUserId(), user.getUserOrdersList(), user.getUserRecipesCount());
//    }
//
//    /**
//     * Removes user recipe from user's list and from the recipe db
//     * */
//    public void removeUserRecipe(String recipeId){
//        //remove from recipe db
//        DataHelper.bookDatabase.deleteRecipe(recipeId);
//
//        //remove from user db
//        User user = DataHelper.user;
//        user.removeUserRecipe(recipeId);
//        updateUserRecipes(user.getUserId(), user.getUserOrdersList(), user.getUserRecipesCount());
//    }

//    public void updateUserBooks(String userId, ArrayList<String> recipeList, int newSize){
//        this.databaseReference.child(userId)
//                .child("userBooksCount")
//                .setValue(newSize);
//
//        this.databaseReference.child(userId)
//                .child("userBooksList")
//                .setValue(recipeList);
//    }

    /**
     * Adds fave recipe under user. Increase fave count of recipe
     *
     * @param book  recipe of favorite recipe
     * */
    public void addFaveBook(Book book){
        User user = DataHelper.user;
        user.addFaveBook(book);
        updateFaveBooks(user.getUserId(), user.getFaveBooksList(), user.getFaveBooksCount());

        DataHelper.bookDatabase.updateFaveCount(book.getId(), book.getFaveCount());
    }

    /**
     * Removes fave recipe from user's list
     * */
    public void removeFaveBook(Book book){
        String recipeId = book.getId();

        User user = DataHelper.user;
        user.removeFaveBook(book);
        updateFaveBooks(user.getUserId(), user.getFaveBooksList(), user.getFaveBooksCount());

        DataHelper.bookDatabase.updateFaveCount(recipeId, book.getFaveCount());
    }

    public void updateFaveBooks(String userId, ArrayList<String> bookList, int newSize){
        this.databaseReference.child(userId)
                .child("faveBooksCount")
                .setValue(newSize);

        this.databaseReference.child(userId)
                .child("faveBooksList")
                .setValue(bookList);
    }

    
}
