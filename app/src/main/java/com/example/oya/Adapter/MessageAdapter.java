package com.example.oya.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oya.Object.MessageObject;
import com.example.oya.R;

import java.util.Collections;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private static final String TAG = "MessageAdapter";
    private List<MessageObject> messageList = Collections.emptyList();

    public void setMessageList(List<MessageObject> messageList) {
        this.messageList = messageList;
        notifyDataSetChanged();
        Log.d(TAG, "Number of messages: " + messageList.size());
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder holder, final int position) {
        //DISPLAYING MESSAGES ON ACTIVITY(USER TO USER CHAT)
        holder.message.setText(messageList.get(position).getMessage());
        holder.sender.setText(messageList.get(position).getSenderId());


    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView message, sender;
        LinearLayout layout;

        MessageViewHolder(View view) {
            super(view);
            layout = view.findViewById(R.id.layout);

            message = view.findViewById(R.id.message);
            sender = view.findViewById(R.id.sender);
        }
    }
}
