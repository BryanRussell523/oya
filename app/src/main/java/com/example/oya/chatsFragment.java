package com.example.oya;


import static androidx.navigation.ActivityNavigatorDestinationBuilderKt.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oya.Adapter.ChatListAdapter;
import com.example.oya.Object.ChatObject;
import com.example.oya.Object.UserObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import android.Manifest;
import android.widget.LinearLayout;

public class chatsFragment extends Fragment {
    ImageButton fab;
    ImageButton fab2;
    private RecyclerView recyclerview;
    private RecyclerView.Adapter mChatListAdapter;
    private RecyclerView.LayoutManager mChatListLayoutManager;
    ArrayList<ChatObject> chatList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chatsfragment, container, false);
        //return inflater.inflate(R.layout.chatsfragment, null);
        fab = view.findViewById(R.id.fab);

        //WALLET BUTTON
        fab2 = view.findViewById(R.id.fab2);

       // firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        chatList= new ArrayList<>();
        recyclerview= view.findViewById(R.id.recyclerview);

        //GET PERMISSION TO LOAD USERS FROM CONTACT LIST
        getPermissions();
        //INITIALIZE RECYCLER VIEW
        initializeRecyclerView();
        //LOAD CHAT LIST
        getUserChatList();
        //OPEN ALL USERS LIST ACTIVITY
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), selectUser.class);
                startActivity(intent);
            }
        });
        //OPEN OYA PAY
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MAKE ANIMATED SPLASH SCREEN FOR SWITCHING MODES
                Intent intent2 = new Intent(getActivity(), SplashScreenPro.class);
                startActivity(intent2);
            }
        });
        return view;
    }
    private void getUserChatList(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getUid()).child("chat");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot childSnapshot:dataSnapshot.getChildren()){
                        ChatObject chatObject = new ChatObject(childSnapshot.getKey());
                        boolean exists = false;
                        for(ChatObject mChatIterator: chatList) {
                            if (mChatIterator.getChatId().equals(chatObject.getChatId()))
                                exists = true;
                        }
                        if (exists)
                            continue;
                        chatList.add(chatObject);
                        mChatListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void initializeRecyclerView() {
        chatList = new ArrayList<>();
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.setHasFixedSize(false);
        mChatListLayoutManager = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
        recyclerview.setLayoutManager(mChatListLayoutManager);
        mChatListAdapter = new ChatListAdapter(chatList);
        recyclerview.setAdapter(mChatListAdapter);
    }
    //GET PERMISSION TO LOAD USERS FROM CONTACT LIST
    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS}, 1);
        }
    }
}