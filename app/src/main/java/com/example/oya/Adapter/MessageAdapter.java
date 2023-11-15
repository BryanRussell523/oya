package com.example.oya.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.oya.Model.Chat;
import com.example.oya.Model.User;
import com.example.oya.R;
import com.example.oya.userTouserchat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT= 1;


    private Context context;
    private List<Chat> Chats;
    private String imageurl;
    FirebaseUser firebaseUser;
    public MessageAdapter(Context context, List<Chat> Chats,String imageurl) {
        this.Chats = Chats;
        this.context = context;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.senderchatlayout, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.recieverchatlayout, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        //CHECK IF USER HAS A PROFILE PICTURE
        Chat chat = Chats.get(position);
        holder.show_message.setText(chat.getMessage());
        if (imageurl.equals("default")){
            holder.usertouserchatimageview.setImageResource(R.drawable.defaultprfilepic);
            //LOAD USER PROFILE IMAGE FROM DATABASE INTO IMAGE VIEW
        }else{Glide.with(context).load(imageurl).into(holder.usertouserchatimageview);
        }
    }

    @Override
    public int getItemCount() {
        return Chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView show_message;
        public ImageView usertouserchatimageview;


        public ViewHolder(View itemView){
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            usertouserchatimageview =itemView.findViewById(R.id.usertouserchatimageview);

        }
    }
     //CONFIGURE CHAT(MESSAGE) POSITIONS
    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (Chats.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_RIGHT;
        }else{
            return MSG_TYPE_LEFT;
        }

    }
}

