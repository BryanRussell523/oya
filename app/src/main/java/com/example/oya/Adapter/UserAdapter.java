package com.example.oya.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.oya.Model.User;
import com.example.oya.R;
import com.example.oya.userTouserchat;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context context;
    private List<User> Users;
    public UserAdapter(Context context, List<User> Users) {
        this.Users = Users;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chatviewlayout, parent,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        User user = Users.get(position);
        holder.nameofuser.setText(user.getUsername());
        if(user.getImageURL().equals("default")){
            holder.imageviewofuser.setImageResource(R.drawable.defaultprfilepic);
        }else{
            Glide.with(context).load(user.getImageURL()).into(holder.imageviewofuser);
        }
        //CLICK ON USER TO BEGIN CHATTING
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, userTouserchat.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //CARRY USER INFORMATION TO userTouserchat Activity
                intent.putExtra("userid",user.getId());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return Users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nameofuser;
        public ImageView imageviewofuser;

        public ViewHolder(View itemView){
            super(itemView);
            nameofuser = itemView.findViewById(R.id.nameofuser);
            imageviewofuser =itemView.findViewById(R.id.imageviewofuser);

        }
    }
}
