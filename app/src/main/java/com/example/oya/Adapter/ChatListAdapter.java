package com.example.oya.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oya.Object.ChatObject;
import com.example.oya.R;
import com.example.oya.userTouserchat;

import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder> {

    ArrayList<ChatObject> chatList;
    public ChatListAdapter(ArrayList<ChatObject> chatList){
        this.chatList = chatList;
    }
    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);

        ChatListViewHolder rcv = new ChatListViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, final int position) {
        holder.title.setText(chatList.get(position).getChatId());

        //ADD USER INFORMATION TO CHAT ACTIVITY
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),userTouserchat.class);
                Bundle bundle = new Bundle();
                bundle.putString("chatID",chatList.get(holder.getAdapterPosition()).getChatId());
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);

            }
        });
    }
    @Override
    public int getItemCount() {
        return chatList.size();
    }
    public class ChatListViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public LinearLayout layout;
        public ChatListViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.title);
            layout = view.findViewById(R.id.layout);
        }
    }}