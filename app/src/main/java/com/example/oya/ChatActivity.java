package com.example.oya;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.oya.Chat.ChatObject;
import com.example.oya.Chat.MessageAdapter;
import com.example.oya.Chat.MessageObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView mChat;
    private RecyclerView.Adapter mChatAdapter;
    private RecyclerView.LayoutManager mChatLayoutManager;
    ArrayList<MessageObject> messageList;
    String chatID;
    DatabaseReference mChatDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#0045FC"));
        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        //setback button for action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Getting Message From ChatID in ChatAdapter class
        chatID = getIntent().getExtras().getString("chatID");
        //DISPLAYING CHAT MESSAGES IN CHAT ACTIVITY
        mChatDb = FirebaseDatabase.getInstance().getReference().child("chat").child(chatID);
        //BUTTON TO SEND OTHER MEDIA:
//        ImageButton mAddMedia = findViewById(R.id.addMedia);
//        mAddMedia.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openGallery();
//            }
//        });

        //BUTTON TO SEND MESSAGE:
        ImageButton mSend = findViewById(R.id.send);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
        //DISPLAYING CHAT MESSAGES IN CHAT ACTIVITY(EXTENDED)
        getChatMessages();
        initializeRecyclerView();
    }
//    METHOD TO OPEN PHONE GALLERY
    int PICK_IMAGE_INTENT =1;
    ArrayList<String> mediaUriList = new ArrayList<>();
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture(s)"),PICK_IMAGE_INTENT);
    }
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == PICK_IMAGE_INTENT){
                if (data.getClipData() == null){
                    mediaUriList.add(data.getData().toString());
                }else{
                    for (int i = 0; i < data.getClipData().getItemCount(); i++){
                        mediaUriList.add(data.getClipData().getItemAt(i).getUri().toString());

                    }
                }
            }
        }
    }

    private void getChatMessages() {
        mChatDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                if (dataSnapshot.exists()){
                    String text = "",
                            creatorID = "";
                    if (dataSnapshot.child("text").getValue() != null)
                        text = dataSnapshot.child("text").getValue().toString();
                    if (dataSnapshot.child("creator").getValue() != null)
                        creatorID = dataSnapshot.child("creator").getValue().toString();
                    //SENDING CHAT TO CHAT ADAPTER AND UPDATE CHAT ACTIVITY WITH NOTIFY
                    MessageObject mMessage = new MessageObject(dataSnapshot.getKey(), creatorID,text);
                    messageList.add(mMessage);
                    //ALWAYS LOAD CHAT ACTIVITY AT LAST MESSAGE SENT
                    mChatLayoutManager.scrollToPosition(messageList.size()-1);
                    //NOTIFY DATABASE OF NEW CHAT CHANGES
                    mChatAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }
     //METHOD TO SEND MESSAGE INTO DATABASE
    private void sendMessage(){
        EditText mMessage = findViewById(R.id.message);
        if(!mMessage.getText().toString().isEmpty()){
            //USING BUNDLE TO CALL CHAT ACTIVITY TO GET MESSAGE ID
            DatabaseReference newMessageDb = mChatDb.push();
            Map newMessageMap = new HashMap<>();
            newMessageMap.put("text", mMessage.getText().toString());
            newMessageMap.put("creator", FirebaseAuth.getInstance().getUid());
            newMessageDb.updateChildren(newMessageMap);
        }
        mMessage.setText(null);
    }
     //Container For Chat
    private void initializeRecyclerView() {
            messageList  = new ArrayList<>();
            mChat = findViewById(R.id.messageList);
            mChat.setNestedScrollingEnabled(false);
            mChat.setHasFixedSize(false);
            mChatLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
            mChat.setLayoutManager(mChatLayoutManager);
            mChatAdapter = new MessageAdapter(messageList);
            mChat.setAdapter(mChatAdapter);
    }
    //Menu Items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chatmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.inchatcallicon:
                //Search Conversations in current chat
                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.viewcontact:
                //View Contact
                Toast.makeText(this, "Coming In next Version", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.inchatvideocallicon:
                //Switch To Professional Mode
                Toast.makeText(this, "Coming In next Version", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.inchatsearch:
                //Switch To Professional Mode
                Toast.makeText(this, "Coming In next Version", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }}
}