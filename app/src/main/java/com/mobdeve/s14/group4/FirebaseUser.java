package com.mobdeve.s14.group4;

import java.util.ArrayList;

public class FirebaseUser {
    private String userId;

    private String email;
    private String password;
    private String username;

    private String firstName;
    private String lastName;

    private String birthday;

    private ArrayList<String> faveBooksList;

    private UploadImage profilePic;


    public FirebaseUser(){
        this.faveBooksList = new ArrayList<String>();
        this.profilePic = getProfile_Image();
    }

    public FirebaseUser(String id, String fName, String lName){
        this.userId = id;
        this.firstName = fName;
        this.lastName = lName;
    }

    public FirebaseUser(String email, String  password, String username, String firstName, String lastName){
        this.email = email;
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;

//        this.userOrdersList = new ArrayList<String>();
        this.faveBooksList = new ArrayList<String>();
    }

    public FirebaseUser(String email, String  password, String username, String firstName, String lastName, String birthday){
        this.email = email;
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;

//        this.userOrdersList = new ArrayList<String>();
        this.faveBooksList = new ArrayList<String>();
    }

    public UploadImage getProfile_Image (){
        return this.profilePic;
    }

    public void setProfile_Image (UploadImage p){
        this.profilePic = p;
    }

    public String getBirthday(){
        return this.birthday;
    }

    public void setBirthday (String b){
        birthday = b;
    }

    public String getUserId(){
        return this.userId;
    }

    public String getUsername(){
        return this.username;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
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

    public void setUsername(String username){
        this.username = username;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        //TODO: apply hash
        this.password = password;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
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

        user.setUsername(this.username);
        user.setEmail(this.email);
        user.setPassword(this.password);

        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);

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
