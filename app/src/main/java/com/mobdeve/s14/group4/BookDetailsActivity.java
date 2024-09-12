package com.mobdeve.s14.group4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class BookDetailsActivity extends AppCompatActivity {
    private Book book;

    private TextView tvBookName;
    private ImageView ivBookPic;
    private TextView tvBookNameTop;
    private TextView tvStarsSummary;
    private TextView tvFavCount;
    private ImageView ivContributorPic;
    private TextView tvContributorName;
    private TextView tvDescription;
    private TextView tvReviewCount;
    private TextView tvCategory;

    private TextView tvIngredients;
    private TextView tvInstructions;
    private TextView tvReviews;

    private TextView tvReviewName;
    private TextView tvReviewComment;

    private ConstraintLayout clIngredients;
    private ConstraintLayout clInstructions;
    private ConstraintLayout clReviews;
    private LinearLayout llWriteReview;
    private LinearLayout llComment2;
    private LinearLayout llIngredientsCont;
    private LinearLayout llStepsCont;
    private LinearLayout llCommentCont;

    private ImageButton ibFave;
    private ImageButton ibBack;
    private ImageView ivDeleteComment;
    private ImageView ivReviewUserPic;

    private FloatingActionButton fabHeart;
    private Boolean liked = false;
    public static Boolean reviewed = false;

    public static final String KEY_BOOK_ID = "KEY_BOOK_ID";

    private TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        this.fabHeart = findViewById(R.id.fab_heart);
        this.tvBookName = findViewById(R.id.tv_book_details_name);
        this.ivBookPic = findViewById(R.id.iv_book_details_pic);
        this.tvBookNameTop = findViewById(R.id.tv_book_details_name_top);
        this.tvStarsSummary = findViewById(R.id.tv_details_stars_summary);
        this.tvFavCount = findViewById(R.id.tv_details_fav_count);
        this.ivContributorPic = findViewById(R.id.iv_book_details_contributor_pic);
        this.tvContributorName = findViewById(R.id.tv_book_details_contributor_name);
        this.tvDescription = findViewById(R.id.tv_book_details_description);
        this.tvReviewCount = findViewById(R.id.tv_details_review_count);
        this.tvCategory = findViewById(R.id.tv_book_details_category);

        this.ivReviewUserPic = findViewById(R.id.iv_review_user_pic);
        this.tvReviewComment = findViewById(R.id.tv_review_comment);
        this.tvReviewName = findViewById(R.id.tv_review_name);

        this.llWriteReview = findViewById(R.id.ll_write_review);
        this.clIngredients = findViewById(R.id.cl_details_ingredients);
        this.clInstructions = findViewById(R.id.cl_details_instructions);
        this.clReviews = findViewById(R.id.cl_details_community_review);
        //this.llComment2 = findViewById(R.id.ll_comment2);

        this.tvIngredients = findViewById(R.id.tv_details_ingredients);
        this.tvInstructions = findViewById(R.id.tv_details_instructions);
        this.tvReviews = findViewById(R.id.tv_details_reviews);
        this.llIngredientsCont = findViewById(R.id.ll_ingredients_cont);
        this.llStepsCont = findViewById(R.id.ll_steps_cont);
        this.llCommentCont = findViewById(R.id.ll_comment_container);
        this.tvEmpty = findViewById(R.id.tv_review_empty);

        this.ibBack = findViewById(R.id.ib_book_details_back);

        //this.ivDeleteComment = findViewById(R.id.iv_delete_comment);

        //this.clReviews = findViewById(R.id.cl_details_reviews);

        Intent i = getIntent();
        String id = i.getStringExtra(PopularAdapter.KEY_BOOK_ID);

        this.book = DataHelper.bookDatabase.findBook(id);

        this.tvBookName.setText(book.getBookName());
//        this.ivRecipePic.setImageResource(recipe.getRecipePic());
        this.tvDescription.setText(book.getDescription());
        this.tvBookNameTop.setText(book.getBookName());
        //this.tvStarsSummary.setText(String.valueOf(recipe.getRating()));
        this.tvFavCount.setText(String.valueOf(book.getFaveCount()));
        this.tvReviewCount.setText(String.valueOf(book.getReviewCount()).concat(" reviews"));

        String temp = "Category: ".concat(book.getCategory());
        this.tvCategory.setText(temp);


//        // set user
//        this.tvContributorName.setText(book.getContributorName());
//
//        // set contrib pic
//        if(book.getContributorPic() != null){
//            Picasso.with(this)
//                    .load(book.getContributorPic().getmImageUrl())
//                    .placeholder(R.drawable.vectorperson)
//                    .fit()
//                    .centerCrop()
//                    .into(ivContributorPic);
//        }
//        else{
//            ivContributorPic.setImageResource(R.drawable.vectorperson);
//        }

        //set pic
        Picasso.with(this)
                .load(book.getUploadImage().getmImageUrl())
                .placeholder(R.drawable.perfect_plate_transparent_bg)
                .fit()
                .centerCrop()
                .into(this.ivBookPic);

        //set liked
        for (Book tempBook : DataHelper.user.getFaveBooks()){
            if (book.getId().equals(tempBook.getId())){
                setLiked();
                break;
            }
        }
//
//        // set ingredients
//        for (Ingredient ingredient : book.getIngredientDetailsList()){
//            View ingredientLayout = getLayoutInflater().inflate(R.layout.ingredients_list_template, llIngredientsCont, false);
//            llIngredientsCont.addView(ingredientLayout);
//
//            TextView measurement = ingredientLayout.findViewById(R.id.tv_ingredients_amt);
//            TextView ingrName = ingredientLayout.findViewById(R.id.tv_ingredient_name);
//
//            String tempM = String.valueOf(ingredient.getQuantity()).concat(" " + ingredient.getUnits());
//            measurement.setText(tempM);
//
//            ingrName.setText(ingredient.getIngredientName());
//        }

//        //set steps
//        for (int ctr = 0; ctr < book.getStepsList().size(); ctr++){
//            View stepsLayout = getLayoutInflater().inflate(R.layout.steps_list_template, llStepsCont, false);
//            llStepsCont.addView(stepsLayout);
//
//            TextView number = stepsLayout.findViewById(R.id.tv_step_number);
//            TextView str = stepsLayout.findViewById(R.id.tv_step_text);
//
//            number.setText(String.valueOf(ctr+1));
//            str.setText(book.getStepsList().get(ctr));
//
//        }
        llCommentCont.removeAllViews();
        // load the reviews for this recipe
        loadReviews();

        this.clIngredients.setVisibility(View.VISIBLE);

        this.llWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewed = true;
                Intent i = new Intent(v.getContext(), WriteReviewActivity.class);
                i.putExtra(KEY_BOOK_ID, id); //TODO: ???
                startActivity(i);
            }
        });

        this.tvIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clIngredients.setVisibility(View.VISIBLE);
                tvIngredients.setBackgroundResource(R.color.proj_yellow);
                tvIngredients.setTextColor(getResources().getColor(R.color.proj_white));


                clInstructions.setVisibility(View.GONE);
                tvInstructions.setBackgroundColor(getResources().getColor(R.color.proj_white));
                tvInstructions.setTextColor(getResources().getColor(R.color.proj_blue));

                clReviews.setVisibility(View.GONE);
                tvReviews.setBackgroundColor(getResources().getColor(R.color.proj_white));
                tvReviews.setTextColor(getResources().getColor(R.color.proj_blue));

            }
        });

        this.tvInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clInstructions.setVisibility(View.VISIBLE);
                tvInstructions.setBackgroundColor(getResources().getColor(R.color.proj_yellow));
                tvInstructions.setTextColor(getResources().getColor(R.color.proj_white));

                clIngredients.setVisibility(View.GONE);
                tvIngredients.setBackgroundResource(R.color.proj_white);
                tvIngredients.setTextColor(getResources().getColor(R.color.proj_blue));

                clReviews.setVisibility(View.GONE);
                tvReviews.setBackgroundColor(getResources().getColor(R.color.proj_white));
                tvReviews.setTextColor(getResources().getColor(R.color.proj_blue));
            }
        });

        this.tvReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clReviews.setVisibility(View.VISIBLE);
                tvReviews.setBackgroundColor(getResources().getColor(R.color.proj_yellow));
                tvReviews.setTextColor(getResources().getColor(R.color.proj_white));

                clIngredients.setVisibility(View.GONE);
                tvIngredients.setBackgroundResource(R.color.proj_white);
                tvIngredients.setTextColor(getResources().getColor(R.color.proj_blue));

                clInstructions.setVisibility(View.GONE);
                tvInstructions.setBackgroundColor(getResources().getColor(R.color.proj_white));
                tvInstructions.setTextColor(getResources().getColor(R.color.proj_blue));
            }
        });

        this.fabHeart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!liked){
                    fabHeart.setImageResource(R.drawable.heart_on);
                    liked = true;
                    fabHeart.setColorFilter(getResources().getColor(R.color.proj_red_pink));
                    DataHelper.userDatabase.addFaveBook(book);
                    tvFavCount.setText(String.valueOf(book.getFaveCount()));
                } else {
                    fabHeart.setImageResource(R.drawable.heart_off);
                    liked = false;
                    fabHeart.setColorFilter(getResources().getColor(R.color.proj_red_pink));
                    DataHelper.userDatabase.removeFaveBook(book);
                    tvFavCount.setText(String.valueOf(book.getFaveCount()));
                }
            }
        });

        this.ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadReviews();

    }

    public void loadReviews(){
        //TODO: convert to recycler view
        llCommentCont.removeAllViews();

        for (Review review : DataHelper.allReviews){
            if (review.getBookId().equals(book.getId())) {
                View commentLayout = getLayoutInflater().inflate(R.layout.comment_template, llCommentCont, false);
                llCommentCont.addView(commentLayout);

                TextView name = commentLayout.findViewById(R.id.tv_review_name);
                TextView comment = commentLayout.findViewById(R.id.tv_review_comment);
                ImageView pic = commentLayout.findViewById(R.id.iv_review_load_pic);
                ImageView ivDelete = commentLayout.findViewById(R.id.iv_delete_comment);
                ImageView ivReviewUserPicture = commentLayout.findViewById(R.id.iv_review_user_pic);

                // set contrib pic
                UserDatabase userDatabase = new UserDatabase();

                User contributor = userDatabase.findUser(review.getContributorId());


                if(contributor.getProfile_Image()!= null){
                    Picasso.with(this)
                            .load(contributor.getProfile_Image().getmImageUrl())
                            .placeholder(R.drawable.vectorperson)
                            .fit()
                            .centerCrop()
                            .into(ivReviewUserPicture);
                }
                else{
                    ivReviewUserPicture.setImageResource(R.drawable.vectorperson);
                }


                if (DataHelper.user.getUserId().equals(review.getContributorId())){
                    ivDelete.setVisibility(View.VISIBLE);
                } else {
                    ivDelete.setVisibility(View.GONE);
                }

                Log.d("review.getId()", review.getId());

                ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DataHelper.reviewDatabase.deleteReview(review);
                        Log.d("DELETEreview.getId()", review.getId());
                        llCommentCont.removeView(commentLayout);
                        loadCounters();

                    }
                });

                name.setText(review.getContributorName());
                comment.setText(review.getComment());

                if (review.getUploadImage() != null){
                    pic.setVisibility(View.VISIBLE);
                    Picasso.with(this)
                            .load(review.getUploadImage().getmImageUrl())
                            .placeholder(R.drawable.perfect_plate_transparent_bg)
                            .fit()
                            .centerCrop()
                            .into(pic);

                } else {
                    pic.setVisibility(View.GONE);
                }
            }
        }

        if(book.getReviewCount() < 1){
            this.tvEmpty.setVisibility(View.VISIBLE);
        } else{
            this.tvEmpty.setVisibility(View.GONE);
        }

//        this.tvStarsSummary.setText(recipe.getRatingString());
//        String revCtr = reviewCount + " reviews";
//        this.tvReviewCount.setText(revCtr);
//        this.tvFavCount.setText(String.valueOf(recipe.getFaveCount()));
        loadCounters();
    }


    public void loadCounters(){
        this.tvStarsSummary.setText(book.getRatingString());

        String revCtr = book.getReviewCount() + " reviews";
        this.tvReviewCount.setText(revCtr);

        this.tvFavCount.setText(String.valueOf(book.getFaveCount()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1){
            llComment2.setVisibility(View.VISIBLE);
        }
    }

    private void setLiked(){
        fabHeart.setImageResource(R.drawable.heart_on);
        liked = true;
        fabHeart.setColorFilter(getResources().getColor(R.color.proj_red_pink));
    }
}