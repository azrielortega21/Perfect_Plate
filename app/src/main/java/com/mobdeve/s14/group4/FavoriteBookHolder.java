package com.mobdeve.s14.group4;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class FavoriteBookHolder extends RecyclerView.ViewHolder{
    private Book book;

    public ImageView ivBook;
    private TextView tvBook;
    public ImageView ivHeart;

    private CardView cvBook;

    private boolean bHeart = true;

    public FavoriteBookHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        ivBook = itemView.findViewById(R.id.template_fr_book_iv_cover);
        tvBook = itemView.findViewById(R.id.template_fr_book_tv_name);
        ivHeart = itemView.findViewById(R.id.template_fr_iv_heart);
        cvBook = itemView.findViewById(R.id.template_fr_cv_book);

        ivHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bHeart == false) {
                    ivHeart.setImageResource(R.drawable.vectorheart_on);
                    bHeart = true;
                    DataHelper.userDatabase.addFaveBook(book);
                }
                else{
                    ivHeart.setImageResource(R.drawable.heart_off);
                    bHeart = false;
                    DataHelper.userDatabase.removeFaveBook(book);
                }
            }
        });
    }

    public void setBook(Book book){ this.book = book; }

    public void setBookPic(int image){
        ivBook.setImageResource(image);
    }

    public void setBookName(String name){
        tvBook.setText(name);
    }

    public CardView getCard (){
        return cvBook;
    }

    public boolean getLiked(){
        return bHeart;
    }
}


