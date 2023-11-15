package com.example.oya;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.oya.Adapter.MessageAdapter;
import com.example.oya.Model.Chat;
import com.example.oya.Model.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class userTouserchat extends AppCompatActivity {
    ImageView wallet;
    ImageButton backbuttonofusertouserchat;
    androidx.appcompat.widget.Toolbar toolbar;
    ImageView usertouserchatimageview;
    TextView nameofspecificchatuser;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    Intent intent;
    androidx.appcompat.widget.AppCompatButton sendmessage;
    EditText message;
    MessageAdapter messageAdapter;
    List<Chat> chat;
    RecyclerView recyclerviewofusertouserchat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_touserchat);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageButton backbuttonofusertouserchat = findViewById(R.id.backbuttonofusertouserchat);
        ImageView usertouserchatimageview = findViewById(R.id.usertouserchatimageview);
        TextView nameofspecificchatuser = findViewById(R.id.nameofspecificchatuser);
        androidx.appcompat.widget.AppCompatButton sendmessage = findViewById(R.id.sendmessage);
        EditText message = findViewById(R.id.message);

        //WALLET BUTTON
        ImageView wallet = findViewById(R.id.wallet);
        //PREPARE RECYCLERVIEW TO LOAD MESSAGES
        recyclerviewofusertouserchat = findViewById(R.id.recyclerviewofusertouserchat);
        recyclerviewofusertouserchat.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerviewofusertouserchat.setLayoutManager(linearLayoutManager);

         intent = getIntent();
         //Get Name of userinformation clicked from select user activity and database(USERNAME)
         String userid = intent.getStringExtra("userid");
         //GETTING MESSAGE TO SEND TO DATABASE ONCLICK OF BUTTON
        sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = message.getText().toString();
                if(!msg.equals("")){
                    sendMessage(firebaseUser.getUid(), userid,msg);
                }else{
                    Toast.makeText(userTouserchat.this, "Message is empty", Toast.LENGTH_SHORT).show();
                }
                message.setText(null);
            }
        });

         firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
         databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        //Get Name of userinformation clicked from select user activity and database(Image)
         databaseReference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 User user = dataSnapshot.getValue(User.class);
                 nameofspecificchatuser.setText(user.getUsername());
                 //IF USER CLICKED ON DOESNT HAVE A PROFILE PICTURE, LOAD DEFAULT PICTURE
                 if(user.getImageURL().equals("default")){
                     usertouserchatimageview.setImageResource(R.drawable.defaultprfilepic);
                     //IF USER HAS A PROFILE PICTURE, LOAD IT FROM DATABASE
                 }else{
                     Glide.with(userTouserchat.this).load(user.getImageURL()).into(usertouserchatimageview);

                 }
                 readMessages(firebaseUser.getUid(),userid,user.getImageURL());

             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });
        backbuttonofusertouserchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(userTouserchat.this, chatViewActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //WALLET BUTTON FUNCTION
        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(findViewById(R.id.layout),"In-chat Payment Function",Snackbar.LENGTH_SHORT).show();
            }
        });
    }
    //SENDING MESSAGE TO DATABASE
    private void sendMessage(String sender, String receiver, String message){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        databaseReference.child("Chats").push().setValue(hashMap);

    }
    private void readMessages(String myid, String userid, String imageurl){
        chat = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat1 = snapshot.getValue(Chat.class);
                    if (chat1.getReceiver().equals(myid)&& chat1.getSender().equals(userid) ||
                            chat1.getReceiver().equals(userid) && chat1.getSender().equals(myid)){
                        chat.add(chat1);
                    }
                    messageAdapter = new MessageAdapter(userTouserchat.this,chat,imageurl);
                    recyclerviewofusertouserchat.setAdapter(messageAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
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

