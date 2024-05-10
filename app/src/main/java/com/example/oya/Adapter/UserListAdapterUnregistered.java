//package com.example.oya.Adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//import com.example.oya.Object.UserObject;
//import com.example.oya.R;
//
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//public class UserListAdapterUnregistered extends RecyclerView.Adapter<UserListAdapterUnregistered.UserListViewHolder> {
//    private ArrayList<UserObject> unregisteredContacts;
//    private OnContactClickListener listener;
//
//    public UserListAdapterUnregistered(ArrayList<UserObject> unregisteredContacts, OnContactClickListener listener) {
//        unregisteredContacts = unregisteredContacts;
//        listener = listener;
//    }
//
//    @NonNull
//    @Override
//    public UserListAdapterUnregistered.UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.userviewlayout, parent, false);
//        return new UserListViewHolder(layoutView);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull UserListAdapterUnregistered.UserListViewHolder holder, int position) {
//        UserObject contact = unregisteredContacts.get(position);
//        holder.bind(contact);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return unregisteredContacts != null ? unregisteredContacts.size() : 0;
//    }
//
//    public class UserListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        public TextView nameofuser, phone;
//        public ImageView userImage;
//        public LinearLayout UserItem;
//
//        public UserListViewHolder(@NonNull View itemView) {
//            super(itemView);
//            userImage = itemView.findViewById(R.id.userImage);
//            nameofuser = itemView.findViewById(R.id.nameofuser);
//            phone = itemView.findViewById(R.id.phone);
//            UserItem = itemView.findViewById(R.id.userImage);
//            itemView.setOnClickListener(this);
//        }
//
//        public void bind(UserObject contact) {
//            nameofuser.setText(contact.getUsername());
//            phone.setText(contact.getPhoneNumber());
//        }
//
//        @Override
//        public void onClick(View v) {
//
//        }
//    }
//
//    public interface OnContactClickListener {
//        void onContactClick(UserObject contact);
//    }
//}
