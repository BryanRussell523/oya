package com.example.oya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileView extends AppCompatActivity {
    private ImageView userImage,close;
    private TextView name, Userphone, des, UserName;
    private String userId;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_view);
        UserName = findViewById(R.id.UserName);//Displaying UserName for now

        Userphone = findViewById(R.id.Userphone);
        userImage = findViewById(R.id.userImage);
        des = findViewById(R.id.des);
        close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            // Retrieve the extras from the intent
            String userId = getIntent().getStringExtra("USER_ID");
            String phone = getIntent().getStringExtra("USER_PHONE");
            String imageUrl = getIntent().getStringExtra("USER_IMAGE_URL");
            String userDescription = getIntent().getStringExtra("USER_DESCRIPTION");

            Userphone.setText(phone);
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.defaultprofilepic)
                    .error(R.drawable.defaultprofilepic);
            Glide.with(this)
                    .load(imageUrl)
                    .apply(requestOptions)
                    .into(userImage);
            if (userDescription != null && !userDescription.isEmpty()) {
                des.setText(userDescription);
            } else {
                des.setVisibility(View.GONE);
            }

            // Check if userId is not null before accessing the database
            if (userId != null) {
                userRef = FirebaseDatabase.getInstance().getReference().child("user").child(userId);
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String username = dataSnapshot.child("username").getValue(String.class);
                            // Update the UserName TextView with the retrieved UserName
                            if (username != null) {
                                UserName.setText(username);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled event if needed
                    }
                });
            } else {
                // Handle the case where userId is null
                // For example, show an error message or handle it based on your app's logic
            }
        }
    }

    }