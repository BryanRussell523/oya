package com.example.oya;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oya.Adapter.RecentChatAdapter;
import com.example.oya.Object.MessageObject;
import com.example.oya.Object.RecentChatObject;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class chatsFragment extends Fragment implements RecentChatAdapter.OnItemClickListener{
    private ImageView search, newChat, more;
    private RecyclerView recyclerView;
    private RecentChatAdapter recentChatAdapter;
    private List<RecentChatObject> recentChatList;
    private androidx.appcompat.widget.Toolbar toolbar;

    private DatabaseReference userChatsRef;
    private DatabaseReference chatsRef;

    private FirebaseUser currentUser;

    public chatsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chatsfragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Setup toolbar
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        //Initialize Firebase components
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // Handle the situation where the user is not signed in
            // For example, you can redirect the user to the sign-in activity
            startActivity(new Intent(getActivity(), SignUp.class));
            // Make sure to return here to avoid further execution of the fragment
            return view;
        }
        // Initialize Firebase components
        userChatsRef = FirebaseDatabase.getInstance().getReference().child("user_chats").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        chatsRef = FirebaseDatabase.getInstance().getReference().child("chats");

        recentChatList = new ArrayList<>();
        recentChatAdapter = new RecentChatAdapter(getContext(), recentChatList,this);
        recyclerView.setAdapter(recentChatAdapter);
        loadRecentChats();


        // Initialize views
        search = view.findViewById(R.id.search);
        newChat = view.findViewById(R.id.newChat);
        more = view.findViewById(R.id.more);

        // Set click listeners
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Search", Toast.LENGTH_SHORT).show();
            }
        });
        newChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), selectUser.class);
                startActivity(intent);
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomMenu(v);
            }
        });
        return view;
    }

    private void showCustomMenu(View anchorView) {
        // Inflate the custom layout
        View customMenuView = getLayoutInflater().inflate(R.layout.chats_custom_menu, null);
        PopupWindow popupWindow = new PopupWindow(customMenuView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(8f);
        }
        // Calculate screen dimensions
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;

        // Calculate the position for the popup window
        int popupWidth = customMenuView.getMeasuredWidth();
        int popupHeight = customMenuView.getMeasuredHeight();
        int x = screenWidth - popupWidth - 200; // Adjusted by 200 pixels to move away from the right edge
        int y = screenHeight / 8 - popupHeight;

        // Show the popup window at the calculated position
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, x, y);

        customMenuView.findViewById(R.id.moment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create Clicked action
                Toast.makeText(requireContext(), "Moments Settings", Toast.LENGTH_SHORT).show();
            }
        });
        customMenuView.findViewById(R.id.favorites).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create Clicked action
                Toast.makeText(requireContext(), " Favorite Chats", Toast.LENGTH_SHORT).show();
            }
        });
        customMenuView.findViewById(R.id.archived).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create Clicked action
                Toast.makeText(requireContext(), "Archived Chats", Toast.LENGTH_SHORT).show();
            }
        });
        customMenuView.findViewById(R.id.blocked).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create Clicked action
                Toast.makeText(requireContext(), "Blocked Contacts", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadRecentChats() {
        userChatsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                String chatId = dataSnapshot.getValue(String.class);
                if (chatId != null) {
                    Query query = chatsRef.child(chatId).child("messages")
                            .orderByChild("timestamp")
                            .limitToLast(1); // Fetch only the most recent message
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                                MessageObject message = messageSnapshot.getValue(MessageObject.class);
                                if (message != null) {
                                    String otherUserId;
                                    if (message.getSenderId().equals(currentUser.getUid())) {
                                        otherUserId = message.getReceiverId();
                                    } else {
                                        otherUserId = message.getSenderId();
                                    }
                                    //Fetch users profile image url and phone number using their ID
                                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("user").child(otherUserId);

                                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                                            if (userSnapshot.exists()) {
                                                String profileImageUrl = userSnapshot.child("image").getValue(String.class);
                                                String phoneNumber = userSnapshot.child("phone").getValue(String.class);// Will change to name of contact later on

                                                RecentChatObject recentChat = new RecentChatObject(chatId, message.getSenderId(), message.getReceiverId(), message.getMessageText(), message.getTimestamp(), profileImageUrl, phoneNumber);
                                                recentChatList.add(recentChat);
                                                recentChatAdapter.notifyDataSetChanged();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Handle child changed event if needed
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
            // Implement other methods of ChildEventListener as needed
        });
    }
    @Override
    public void onItemClick(RecentChatObject chat) {
        // Handle click on each user on the recent chat list and open the UserChatActivity
        Intent intent = new Intent(getContext(), UserchatActivity.class);
        intent.putExtra("CHAT_ID", chat.getChatId());
        intent.putExtra("OTHER_USER_ID", chat.getOtherUserId());
        intent.putExtra("OTHER_USER_PHONE", chat.getPhoneNumber());
        intent.putExtra("OTHER_USER_IMAGE_URL", chat.getProfileImageUrl());
        getContext().startActivity(intent);

    }
}
