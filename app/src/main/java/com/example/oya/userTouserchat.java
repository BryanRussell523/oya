package com.example.oya;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oya.Adapter.MessageAdapter;
import com.example.oya.Object.MessageObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class userTouserchat extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{ androidx.appcompat.widget.Toolbar toolbar;
    TextView nameofspecificchatuser;
    androidx.appcompat.widget.AppCompatButton sendmessage,more;
    private RecyclerView recyclerviewofusertouserchat;
    private MessageAdapter mMessageAdapter;
    private RecyclerView.LayoutManager mMessageLayoutManager;
    //GET CHAT ID INTO THIS ACTIVITY
    String chatID;
    DatabaseReference mChatDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_touserchat);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nameofspecificchatuser = findViewById(R.id.nameofspecificchatuser);

        more = findViewById(R.id.more);
        //LOADING MESSAGES TO RECYCLER VIEW
        initializeRecyclerView();
        //DISPLAY CHATS ON RECYCLER VIEW
        getChatMessages();
        //GET CHAT ID FROM CHAT LIST ADAPTER
        chatID = getIntent().getExtras().getString("chatID");
        //DISPLAY CHAT MESSAGES
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference mChatDb = firebaseDatabase.getReference().child("chat").child(chatID);


        //BUTTON TO SEND MESSAGE
        sendmessage = findViewById(R.id.sendmessage);
        sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }
    //METHOD (sendMessage)
    private void sendMessage(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference mChatDb = firebaseDatabase.getInstance().getReference();
        EditText dismessage = findViewById(R.id.dismessage);
        if(!dismessage.getText().toString().isEmpty()){
            DatabaseReference newMessageDb = mChatDb.push();
            Map newMessageMap = new HashMap<>();
            newMessageMap.put("text",dismessage.getText().toString());
            newMessageMap.put("creator", FirebaseAuth.getInstance().getUid());
            newMessageDb.updateChildren(newMessageMap);
        }
        dismessage.setText(null);
    }
    //DISPLAY CHAT MESSAGES
    private void getChatMessages(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference mChatDb = firebaseDatabase.getReference().child("chat");

        mChatDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("chat","Is method working or not?");


                List<MessageObject> messageObjectList = new ArrayList<>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    MessageObject messageObject = dataSnapshot1.getValue(MessageObject.class);
                    messageObjectList.add(messageObject);
                }
                mMessageAdapter.setMessageList(messageObjectList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    //METHOD TO MAKE RECYCLERVIEW LOAD MESSAGES
    private void initializeRecyclerView() {
        //PREPARE RECYCLERVIEW TO LOAD MESSAGES
        recyclerviewofusertouserchat = findViewById(R.id.recyclerviewofusertouserchat);
        recyclerviewofusertouserchat.setNestedScrollingEnabled(false);
        recyclerviewofusertouserchat.setHasFixedSize(false);
        mMessageLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerviewofusertouserchat.setLayoutManager(mMessageLayoutManager);
        mMessageAdapter = new MessageAdapter();
        recyclerviewofusertouserchat.setAdapter(mMessageAdapter);
    }
    //POP UP MENU
    public void showPopup(View v){
        PopupMenu popupMenu = new PopupMenu(this,v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.gallery) {
            Toast.makeText(this, "gallery", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (itemId == R.id.avatar) {
            Toast.makeText(this, "avatar", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (itemId == R.id.wallet) {
            Toast.makeText(this, "wallet", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (itemId == R.id.contact) {
            Toast.makeText(this, "contact", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (itemId == R.id.location) {
            Toast.makeText(this, "location", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(userTouserchat.this, chatViewActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuofuserchat, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();
        if (itemId == R.id.search_bar) {
            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (itemId == R.id.call) {
            Toast.makeText(this, "Call", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (itemId == R.id.videocall) {
            Toast.makeText(this, "Video call", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

}