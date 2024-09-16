package com.mobdeve.s14.group4;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<AdminViewHolder>{

    private ArrayList<User> admins;

    public AdminAdapter(ArrayList<User> a){
        this.admins = a;
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.admin_list_template, parent, false);

        AdminViewHolder holder = new AdminViewHolder(itemView);

        return holder;
    }

    private void deleteAdmin(String email) {
        new UserDatabase().getAllUsers(new CallbackListener() {
            @Override
            public void onSuccess(Object o) {
                ArrayList<User> users= (ArrayList<User>) o;
                for (User u : users){
                    if (u.getEmail().equals(email)){
                        u.setAdmin(false);
                        new UserDatabase().updateUser(u);
                    }
                }
            }

            @Override
            public void onFailure() {
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {
        holder.setTvEmail(admins.get(position).getEmail());
        holder.setTvName(admins.get(position).getFullName());

        if(DataHelper.user.getEmail().equals(holder.getEmail())){
            holder.getIBDelete().setVisibility(View.GONE);
        }
        else {
            holder.getIBDelete().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Delete Admin
                    deleteAdmin(holder.getEmail());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return admins.size();
    }
}
