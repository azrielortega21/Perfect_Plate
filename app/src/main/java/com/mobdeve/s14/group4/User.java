package com.mobdeve.s14.group4;

import java.util.ArrayList;

public class User extends FirebaseUser{
    private ArrayList<Book> faveBooks;

    public User(){}

    public User(FirebaseUser user){
        setUserId(user.getUserId());

        setFullName(user.getFullName());
        setEmail(user.getEmail());
        setPassword(user.getPassword());
        setAddress(user.getAddress());

        setProfile_Image(user.getProfile_Image());

        setFaveBooksList(user.getFaveBooksList());

        initializeBookLists();
    }

    public User(String fullName, String email, String  password, Address address){
        super(fullName, email, password, address);

//        this.userBooks = new ArrayList<Book>();
        this.faveBooks = new ArrayList<Book>();
    }

    private void initializeBookLists(){
        BookDatabase bookDatabase = new BookDatabase();

        this.faveBooks = bookDatabase.findBooks(getFaveBooksList());

        DataHelper.userDatabase.updateFaveBooks(getUserId(), getFaveBooksList(), getFaveBooksCount());
    }

//    //add user recipe to current list of recipes
//    public void addUserRecipe(Book book){
//        //add to local lists
//        this.userBooks.add(book);
//        addUserOrderId(book.getId());
//    }

    public void addFaveBook(Book book){
        this.faveBooks.add(book);
        addFaveBookId(book.getId());

        book.setFaveCount(book.getFaveCount() + 1);
    }

//    /**
//     * Removes user recipe from User and FirebaseUser
//     */
//    public void removeUserRecipe(String id){
//        int removeIndex = 0;
//
//        for (int i = 0; i < getUserRecipesCount(); i++){
//            if (this.userBooks.get(i).getId().equals(id)){
//                removeIndex = i;
//            }
//        }
//
//        removeUserRecipeId(id);
//        this.userBooks.remove(removeIndex);
//    }

    /**
     * Removes favorite recipe from User and FirebaseUser
     */
    public void removeFaveBook(Book book){
        int removeIndex = 0;

        for (int i = 0; i < getFaveBooksCount(); i++){
            if (this.faveBooks.get(i).getId().equals(book.getId())){
                removeIndex = i;
            }
        }

        removeFaveBookId(book.getId());
        this.faveBooks.remove(removeIndex);

        book.setFaveCount(book.getFaveCount() - 1);
    }

//    public ArrayList<Book> getUserRecipes(){
//        return this.userBooks;
//    }

    public ArrayList<Book> getFaveBooks(){
        return this.faveBooks;
    }

    public FirebaseUser getFirebaseUser(){
        return duplicateUser();
    }
}
