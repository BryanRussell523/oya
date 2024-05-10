package com.example.oya.Adapter;

import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oya.Object.MessageObject;
import com.example.oya.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private static final int VIEW_TYPE_MESSAGE_SENT =1 ;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private Context context;
    private List<MessageObject> messageList;
    private String senderId;

    public MessageAdapter(Context context, List<MessageObject> messageList, String senderId) {
        this.context = context;
        this.messageList = messageList;
        this.senderId = senderId;
    }
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(context).inflate(R.layout.senderchatlayout, parent,false);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.receiverchatlayout, parent, false);
        }

        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        MessageObject message = messageList.get(position);
        holder.bind(message);

    }
    @Override
    public int getItemCount() {
        return messageList.size();
    }
    public int getItemViewType(int position){
        MessageObject message = messageList.get(position);
        if (message != null && message.getSenderId() != null && message.getSenderId().equals(senderId)){
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{
        TextView messageText;
        TextView messageTime;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            messageTime = itemView.findViewById(R.id.messageTime);
        }
        void bind(MessageObject message){
            messageText.setText(message.getMessageText());

            //Convert time tamp to readable time format
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String formattedTime = sdf.format(new Date(message.getTimestamp()));
            messageTime.setText(formattedTime);
        }
    }
}

