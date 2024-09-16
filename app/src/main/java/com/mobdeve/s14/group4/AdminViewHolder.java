package com.mobdeve.s14.group4;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdminViewHolder extends RecyclerView.ViewHolder{

    private TextView tvName;
    private TextView tvEmail;

    private ImageButton ibDelete;

    public AdminViewHolder(@NonNull View itemView) {
        super(itemView);

        this.tvName = itemView.findViewById(R.id.tv_admin_name);
        this.tvEmail = itemView.findViewById(R.id.tv_admin_email);

        this.ibDelete = itemView.findViewById(R.id.ib_admin_remove);
    }

    public void setTvName (String n){
        this.tvName.setText(n);
    }

    public void setTvEmail (String e){
        this.tvEmail.setText(e);
    }

    public String getEmail (){ return this.tvEmail.getText().toString().trim();}

    public ImageButton getIBDelete (){
        return this.ibDelete;
    }
}
