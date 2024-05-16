package com.example.oya.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.oya.Object.UserObject;
import com.example.oya.R;
import com.example.oya.UserchatActivity;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {
    private List<UserObject> userList;
    private Context context;

    public UserListAdapter(Context context, List<UserObject> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userviewlayout, parent, false);
        return new UserViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserObject user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameofuser;
        private ImageView userImage;
        private UserObject user;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            nameofuser = itemView.findViewById(R.id.nameofuser);
            userImage = itemView.findViewById(R.id.userImage);
            itemView.setOnClickListener(this);
        }public void bind(UserObject user) {
            this.user = user;
            nameofuser.setText(user.getPhone());
            Log.d("UserListAdapter505", "User Name: " + user.getName());

            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.defaultprofilepic)
                    .error(R.drawable.defaultprofilepic)
                    .transform(new CircleCrop());
            Glide.with(itemView.getContext())
                    .load(user.getProfileImageUrl())
                    .apply(requestOptions)
                    .into(userImage);
            Log.d("UserListAdapter505", "Profile Image URL: " + user.getProfileImageUrl());
        }
        @Override
        public void onClick(View v) {
            if (user != null) {
                Context context = itemView.getContext();
                Intent intent = new Intent(context, UserchatActivity.class);
                intent.putExtra("OTHER_USER_ID", user.getUid());
                intent.putExtra("USER_PHONE", user.getPhone());
                intent.putExtra("USER_IMAGE_URL", user.getProfileImageUrl());
                context.startActivity(intent);
            }
        }
    }
}
