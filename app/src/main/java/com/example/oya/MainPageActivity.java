package com.example.oya;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.oya.Chat.ChatListAdapter;
import com.example.oya.Chat.ChatObject;
import com.example.oya.databinding.ActivityMainBinding;
import com.example.oya.databinding.ActivityMainPageBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.content.Intent;
//GETTING PERMISSION FROM ANDROID MANIFEST
import android.Manifest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainPageActivity extends AppCompatActivity {
    private RecyclerView mChatList;
    private RecyclerView.Adapter mChatListAdapter;
    private RecyclerView.LayoutManager mChatListLayoutManager;
    ArrayList<ChatObject> chatList;
    FloatingActionButton fab;
    TextView story,calls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
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
        //Changing Title bar Text
        getSupportActionBar().setTitle("Oya/Chats");  // provide compatibility to all the versions
        // GETTING PERMISSION TO IMPORT PHONE CONTACTS
        getPermissions();
        //Initializing RecyclerView
        initializeRecyclerView();
        //Getting Chat from DataBase
        getUserChatList();
        //MAKE TEXT VIEW(CHAT DISAPPEAR)
        TextView chat = findViewById(R.id.chat);
        chat.postDelayed(new Runnable() {
            public void run() {
                chat.setVisibility(View.INVISIBLE);
            }
        }, 3000);
        //Animated Activity Transition Button(Text View)
        TextView story = findViewById(R.id.story);
        story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),StoryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        TextView calls = findViewById(R.id.calls);
        calls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CallsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });





        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FindUserActivity.class);
                startActivity(intent);

            }
        });
    }
    private void getUserChatList() {
        DatabaseReference mUserChatDB = FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getUid()).child("chat");
        mUserChatDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        ChatObject mChat = new ChatObject(childSnapshot.getKey());
                        boolean exists = false;
                        for (ChatObject mChatIterator : chatList) {
                            if (mChatIterator.getChatId().equals(mChat.getChatId()))
                                exists = true;
                        }
                        if (exists)
                            continue;
                        chatList.add(mChat);
                        mChatListAdapter.notifyDataSetChanged();

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

            //Chat LIST CONTAINER
            private void initializeRecyclerView(){
                chatList = new ArrayList<>();
                mChatList = findViewById(R.id.chatList);
                mChatList.setNestedScrollingEnabled(false);
                mChatList.setHasFixedSize(false);
                mChatListLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                mChatList.setLayoutManager(mChatListLayoutManager);
                mChatListAdapter = new ChatListAdapter(chatList);
                mChatList.setAdapter(mChatListAdapter);
            }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.menu, menu);
                return true;
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.settings:
                        //ADD INTENT TO LOAD SETTINGS ACTIVITY
                        Intent settingsintent = new Intent(MainPageActivity.this, Settings.class);
                        startActivity(settingsintent);
                        return true;
                    case R.id.logout:
                        //LOGOUT FUNCTION
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getApplicationContext(), SignUp.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.search:
                        //Search Conversations
                        Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.pro:
                        //Switch To Professional Mode
                        Toast.makeText(this, "Switch to Professional Mode", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return super.onOptionsItemSelected(item);
                }}
                private void getPermissions(){

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS}, 1);

                }}
            }