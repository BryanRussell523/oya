package com.example.oya.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oya.Object.UserObject;
import com.example.oya.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder>{
    ArrayList<UserObject> userList;

    public UserListAdapter(ArrayList<UserObject> userList){
        this.userList = userList;
    }
    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.userviewlayout, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);

        UserListViewHolder rcv = new UserListViewHolder(layoutView);
        return rcv;
    }
    @Override
    public void onBindViewHolder(@NonNull final UserListViewHolder holder,final int position) {
        holder.nameofuser.setText(userList.get(position).getName());
        holder.phone.setText(userList.get(position).getPhone());

        holder.UserItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = FirebaseDatabase.getInstance().getReference().child("chat").push().getKey();
                FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getUid()).child("chat").child(key).setValue(true);
                FirebaseDatabase.getInstance().getReference().child("user").child(userList.get(position).getUid()).child("chat").child(key).setValue(true);
            }
        });
    }
    @Override
    public int getItemCount() {
        return userList.size();
    }
    class UserListViewHolder extends RecyclerView.ViewHolder{
        public TextView nameofuser, phone;
        public LinearLayout UserItem;
       public UserListViewHolder(View view){
            super(view);
            nameofuser = view.findViewById(R.id.nameofuser);
            phone = view.findViewById(R.id.phone);
            UserItem = view.findViewById(R.id.UserItem);
        }
    }
}