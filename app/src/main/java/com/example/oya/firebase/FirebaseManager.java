package com.example.oya.firebase;

import androidx.annotation.NonNull;

import com.example.oya.Object.RecentChatObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseManager{
    private DatabaseReference recentChatsRef;
    public FirebaseManager(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        recentChatsRef = database.getReference("recent_chats");
    }
    public void updateRecentChat(String chatId, String otherUserId,String lastMessage){
        //Get the current user Id
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = currentuser.getUid();

//        RecentChatObject recentChatObject = new RecentChatObject()
    }



}

