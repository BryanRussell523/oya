package com.example.oya;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class settingsFragment extends Fragment {
    // Variable to store the original status bar color
    private int originalStatusBarColor;
    private ImageView editProfile,userImage;
    private TextView userName,phone,des;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settingsfragment, container, false);
        // Store the original status bar color
        originalStatusBarColor = getActivity().getWindow().getStatusBarColor();
        // Check if the Android version is Lollipop or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Set the status bar color to blue
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.blue));
        }

        editProfile = view.findViewById(R.id.editProfile);
        userImage = view.findViewById(R.id.userImage);
        userName = view.findViewById(R.id.userName);
        phone = view.findViewById(R.id.phone);
        des =view.findViewById(R.id.des);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), editProfileActivity.class);
                startActivity(intent);
            }
        });
        loadUserData();
        return view;
    }
    private void loadUserData() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            phone.setText(currentUser.getPhoneNumber());

            // Fetch and set profile image using the image URL from the database
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("user").child(currentUser.getUid());
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        //Fetch and set Username
                        String username = dataSnapshot.child("username").getValue(String.class);
                        if (username != null) {
                            userName.setText(username);
                        }
                        String profileImageUrl = dataSnapshot.child("image").getValue(String.class);
                        if (profileImageUrl != null) {
                            RequestOptions requestOptions = new RequestOptions()
                                    .placeholder(R.drawable.defaultprofilepic)
                                    .error(R.drawable.defaultprofilepic)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL);
                            Glide.with(requireContext())
                                    .load(profileImageUrl)
                                    .apply(requestOptions)
                                    .into(userImage);
                        } else {
                            // Set a default image if the profile image URL is null
                            userImage.setImageResource(R.drawable.defaultprofilepic);
                        }

                        // Fetch and set description
                        String description = dataSnapshot.child("description").getValue(String.class);
                        if (description != null) {
                            des.setText(description);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Log an error message if the data retrieval is cancelled
                    Log.e("UserData", "Error fetching user data: " + databaseError.getMessage());
                }
            });
        } else {
            // Log a message if the current user is null
            Log.d("CurrentUser", "User is null");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Revert the status bar color to its original state when the fragment is destroyed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(originalStatusBarColor);
        }
    }
}

