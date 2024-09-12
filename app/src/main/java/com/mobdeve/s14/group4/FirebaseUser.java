package com.mobdeve.s14.group4;

import java.util.ArrayList;

public class FirebaseUser {
    private String userId;

    private String fullName;
    private String email;
    private String password;
    private Address address;

    private UploadImage profilePic;

    private ArrayList<String> faveBooksList;

    public FirebaseUser(){
        this.faveBooksList = new ArrayList<String>();
        this.profilePic = getProfile_Image();
    }

    public FirebaseUser(String fullName, String email, String  password, Address address){
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.address = address;

//        this.userOrdersList = new ArrayList<String>();
        this.faveBooksList = new ArrayList<String>();
    }

    public String getUserId(){
        return this.userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    public Address getAddress() {
        return address;
    }

    public UploadImage getProfile_Image (){
        return this.profilePic;
    }

    //    public int getUserRecipesCount(){
//        return this.userOrdersList.size();
//    }

//    public ArrayList<String> getUserOrdersList(){
//        return this.userOrdersList;
//    }

    public int getFaveBooksCount(){
        return this.faveBooksList.size();
    }

    public ArrayList<String> getFaveBooksList(){
        return this.faveBooksList;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        //TODO: apply hash
        this.password = password;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setProfile_Image (UploadImage p){
        this.profilePic = p;
    }

//    public void setUserOrdersList(ArrayList<String> recipeList){
//        this.userOrdersList = recipeList;
//    }

    public void setFaveBooksList(ArrayList<String> bookList){
        this.faveBooksList = bookList;
    }

    public FirebaseUser duplicateUser(){
        FirebaseUser user = new FirebaseUser();

        user.setUserId(this.userId);

        user.setFullName(this.fullName);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setAddress(this.address);

//        user.setUserOrdersList(this.userOrdersList);
        user.setFaveBooksList(this.faveBooksList);

        if(this.profilePic != null) {
            user.setProfile_Image(this.profilePic);
        }
        else{
            user.setProfile_Image(null);
        }

        return user;
    }

    public void addFaveBookId(String id){
        this.faveBooksList.add(id);
    }

    public void removeFaveBookId(String id){
        int n = getFaveBooksCount();
        int removeIndex = 0;

        for (int i = 0; i < n; i++){
            if (this.faveBooksList.get(i).equals(id)){
                removeIndex = i;
            }
        }

        this.faveBooksList.remove(removeIndex);
    }
}
