package com.example.oya.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.oya.Object.RecentChatObject;
import com.example.oya.R;
import com.example.oya.UserchatActivity;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecentChatAdapter extends RecyclerView.Adapter<RecentChatAdapter.RecentChatViewHolder> {
    private Context context;
    private List<RecentChatObject> chatList;
    private OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(RecentChatObject chat);
    }

    public RecentChatAdapter(Context context, List<RecentChatObject> chatList, OnItemClickListener listener) {
        this.context = context;
        this.chatList = chatList;
        this.listener = listener;
    }

    public RecentChatAdapter(Context context, List<RecentChatObject> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public RecentChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_chat_item, parent, false);
        return new RecentChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentChatViewHolder holder, int position) {
        RecentChatObject chat = chatList.get(position);
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.defaultprofilepic)
                .error(R.drawable.defaultprofilepic)
                .circleCrop();
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(chat.getProfileImageUrl())
                .into(holder.userImage);

        holder.userName.setText(chat.getPhoneNumber()); // Will change to name of contact later on
        holder.lastMessage.setText(chat.getLastMessage());

        // Format timestamp
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T' HH:mm:ss.SSS'Z'", Locale.getDefault());
        String timestampString = sdf.format(new Date(chat.getTimestamp()));
        try {
            Date date = sdf.parse(timestampString);
            Date currentDate = new Date();

            // Check if the message was sent today
            if (isSameDay(date, currentDate)) {
                // If yes, display time only
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                String formattedTime = timeFormat.format(date);
                holder.time.setText(formattedTime);
            } else {
                // If no, display date
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
                String formattedDate = dateFormat.format(date);
                holder.time.setText(formattedDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            holder.time.setText(timestampString);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(chat); // Notify the listener when an item is clicked

                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return chatList.size();
    }
    public static class RecentChatViewHolder extends RecyclerView.ViewHolder {
        ImageView userImage;
        TextView userName, time, lastMessage;

        public RecentChatViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.userImage);
            userName = itemView.findViewById(R.id.userName);
            time = itemView.findViewById(R.id.time);
            lastMessage = itemView.findViewById(R.id.lastMessage);
        }
    }

    // Helper method to check if two dates are on the same day
    private static boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString1 = sdf.format(date1);
        String dateString2 = sdf.format(date2);
        return dateString1.equals(dateString2);
    }
}
