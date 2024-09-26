package com.mobdeve.s14.group4;

public class Review {
    private String reviewId;
    private String comment;
    private double rating;
    private String bookId;

    private User contributor;
    private UploadImage image;

    public Review(){
    }

    public Review(User contributor, double rating, String comment, String bookId){
        this.contributor = contributor;
        this.rating = rating;
        this.comment = comment;
        this.bookId = bookId;
    }

    public Review(User contributor, double rating, String comment, String bookId, UploadImage image){
        this.contributor = contributor;
        this.rating = rating;
        this.comment = comment;
        this.bookId = bookId;
        this.image = image;
        System.out.println("REVIEWPIC1 " + image.getmImageUrl());
    }

    public Review(String reviewId, String contributorId, double rating, String comment, String bookId){
        this.reviewId = reviewId;
        setContributorId(contributorId);
        this.rating = rating;
        this.comment = comment;
        this.bookId = bookId;
        this.image = null;
    }

    public Review(String reviewId, String contributorId, double rating, String comment, String bookId, UploadImage image){
        this.reviewId = reviewId;
        setContributorId(contributorId);
        this.rating = rating;
        this.comment = comment;
        this.bookId = bookId;
        System.out.println("REVIEWPIC2 " + image.getmImageUrl());
        this.image = image;
    }

    public void setUploadImage (UploadImage ui){
        this.image = ui;
    }

    public UploadImage getUploadImage (){
        return this.image;
    }

    public String getId(){ return this.reviewId; }

    public String getComment(){
        return this.comment;
    }

    public double getRating(){
        return this.rating;
    }

    public String getContributorId(){
        return this.contributor.getUserId();
    }

    public String getBookId(){
        return this.bookId;
    }

    public String getContributorName() {
        return this.contributor.getFullName();
    }

    public void setId(String id) { this.reviewId = id; }

    public void setComment(String comment){
        this.comment = comment;
    }

    public void setRating(double rating){
        this.rating = rating;
    }

    public void setContributorId(String id){
        this.contributor = DataHelper.userDatabase.findUser(id);
    }

    public void setBookId(String id){
        this.bookId = id;
    }
}
