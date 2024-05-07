package com.example.oya;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.oya.Adapter.MessageAdapter;
import com.example.oya.Object.MessageObject;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.List;

public class UserchatActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private String chatId;
    private Toolbar toolbar;
    private TextView nameofuser, Online, Offline;
    private ImageView back, userImage, extra, sendVoiceMessage, sendMessage;
    LinearLayout document, gallery, audio, games, location;
    RelativeLayout extraMenu;
    private com.google.android.material.textfield.TextInputEditText editTextMessage;
    private RecyclerView recyclerview;
    private RecyclerView.LayoutManager layoutManager;
    private MessageAdapter messageAdapter;
    private List<MessageObject> messageList;

    private boolean isEditTextEmpty = true;
    private DatabaseReference messageRef;

    private String currentUserId;
    private String otherUserId;
    private DatabaseReference getMessageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);
        // Set status bar color to white
        setStatusBarColorToWhite();



        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            currentUserId = currentUser.getUid();
            otherUserId = getIntent().getStringExtra("USER_ID");

            //Generate Chat ID
            chatId = generateChatId(currentUserId, otherUserId);
            messageRef = FirebaseDatabase.getInstance().getReference().child("chats").child(chatId).child("messages");

        }else{
            // Handle the case where current user is null
            Log.e("UserchatActivity", "Current user is null");
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if user is not authenticated
        }
        initializeRecyclerView();
        loadMessages();



        String senderId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        nameofuser = findViewById(R.id.nameofuser);
        userImage = findViewById(R.id.userImage);


        back = findViewById(R.id.back);
        Online = findViewById(R.id.Online);
        Offline = findViewById(R.id.Offline);

        extraMenu = findViewById(R.id.extraMenu);
        extra = findViewById(R.id.extra);

        //The extra menu buttons
        document = findViewById(R.id.document);
        gallery = findViewById(R.id.gallery);
        audio = findViewById(R.id.audio);
        games = findViewById(R.id.games);
        location = findViewById(R.id.location);

        editTextMessage = findViewById(R.id.editTextMessage);
        sendVoiceMessage = findViewById(R.id.sendVoiceMessage);
        sendMessage = findViewById(R.id.sendMessage);

        //Intent from select user activity
        Intent intent = getIntent();
        if (intent != null) {
            String userId = intent.getStringExtra("USER_ID");
            String userPhone = intent.getStringExtra("USER_PHONE");
            String userProfileImageUrl = intent.getStringExtra("USER_IMAGE_URL");

            //Load data into views
            if (userId != null && userPhone != null && userProfileImageUrl != null) {

                nameofuser.setText(userPhone);
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.drawable.defaultprofilepic)
                        .error(R.drawable.defaultprofilepic)
                        .transform(new CircleCrop());
                Glide.with(this)
                        .load(userProfileImageUrl)
                        .apply(requestOptions)
                        .into(userImage);
            }
        }

        recyclerview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Check if the extraMenu is visible and if the touch event is outside its bounds
                if (extraMenu.getVisibility() == View.VISIBLE && !isPointInsideView(event.getRawX(), event.getRawY(), extraMenu)) {
                    extraMenu.setVisibility(View.GONE);
                    return true;
                }
                return false;
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserchatActivity.this, homeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        extra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraMenu.setVisibility(View.VISIBLE);
            }
        });
        editTextMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isEditTextEmpty = charSequence.toString().trim().isEmpty();
                updateButtonsVisibility();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("user").child(otherUserId);
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String phone = dataSnapshot.child("phone").getValue(String.class);
                            String imageUrl = dataSnapshot.child("image").getValue(String.class);
                            String userDescription = dataSnapshot.child("description").getValue(String.class);
                            Intent intent = new Intent(UserchatActivity.this, UserProfileView.class);
                            // Pass the necessary data as extras to the intent
                            intent.putExtra("USER_ID", otherUserId);
                            intent.putExtra("USER_PHONE", phone);
                            intent.putExtra("USER_IMAGE_URL", imageUrl); // Make sure to replace "imageUrl" with the actual URL
                            intent.putExtra("USER_DESCRIPTION", userDescription); // Add this line if you have a description to pass
                            // Start the next activity
                            startActivity(intent);
                        } else {
                            Toast.makeText(UserchatActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        nameofuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("user").child(otherUserId);
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String phone = dataSnapshot.child("phone").getValue(String.class);
                            String imageUrl = dataSnapshot.child("image").getValue(String.class);
                            String userDescription = dataSnapshot.child("description").getValue(String.class);
                            Intent intent = new Intent(UserchatActivity.this, UserProfileView.class);
                            // Pass the necessary data as extras to the intent
                            intent.putExtra("USER_ID", otherUserId);
                            intent.putExtra("USER_PHONE", phone);
                            intent.putExtra("USER_IMAGE_URL", imageUrl); // Make sure to replace "imageUrl" with the actual URL
                            intent.putExtra("USER_DESCRIPTION", userDescription); // Add this line if you have a description to pass
                            // Start the next activity
                            startActivity(intent);
                        } else {
                            Toast.makeText(UserchatActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
    private void initializeRecyclerView() {
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.setHasFixedSize(false);

        messageList = new ArrayList<>();

        messageAdapter = new MessageAdapter(this, messageList, FirebaseAuth.getInstance().getCurrentUser().getUid());
        // Set the LinearLayoutManager with reverse layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        layoutManager.setStackFromEnd(true); // Optional: Keep items at the bottom
        recyclerview.setLayoutManager(layoutManager);

        recyclerview.setAdapter(messageAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_chat_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.videocall) {
            Toast.makeText(this, "Video call", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (itemId == R.id.call) {
            Toast.makeText(this, "Call", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (itemId == R.id.more) {
            Toast.makeText(this, "More", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    private String generateChatId(String currentUserId, String otherUserId) {
        return currentUserId.compareTo(otherUserId) < 0 ? currentUserId + "_" + otherUserId : otherUserId + "_" + currentUserId;
    }

    private void setStatusBarColorToWhite() {
        Window window = getWindow();
        // Check if the Android version is at least Lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Set the status bar color to white
            window.setStatusBarColor(getResources().getColor(android.R.color.white));
        }
        // Check if the Android version is at least Marshmallow
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Set the status bar icons to dark color
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
    public void loadMessages() {
        messageRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                MessageObject message = snapshot.getValue(MessageObject.class);
                if (message != null) {
                    // Add message to the list and notify adapter
                    Log.d("UserchatActivity32", "New message added: " + message.getMessageText()); // Log the new message

                    messageList.add(message);
                    messageAdapter.notifyItemInserted(messageList.size() -1);
                    recyclerview.scrollToPosition(messageList.size() -1);
                    Log.d("UserchatActivity32", "Scrolling to position: " + (messageList.size() - 1)); // Log the position to scroll to


                }else{
                    Log.e("UserchatActivty32", "Message is null");
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
        });
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        extraMenu.setVisibility(View.GONE);
        return true;
    }
    private boolean isPointInsideView(float x, float y, View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];

        //Check if the touch event coordinates are inside the bounds of the view
        return (x > viewX && x < (viewX + view.getWidth()) && y > viewY && y < (viewY + view.getHeight()));
    }

    private void updateButtonsVisibility() {
        if (isEditTextEmpty) {
            sendVoiceMessage.setVisibility(View.VISIBLE);
            sendMessage.setVisibility(View.GONE);
        } else {
            sendVoiceMessage.setVisibility(View.GONE);
            sendMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UserchatActivity.this, homeActivity.class);
        startActivity(intent);
        finish();
    }

    private void sendMessage() {
        String messageText = editTextMessage.getText().toString().trim();
        if (!messageText.isEmpty()) {
            long timestamp = System.currentTimeMillis();
            String messageId = messageRef.push().getKey();
            MessageObject message = new MessageObject(messageId, currentUserId, otherUserId, messageText, timestamp);
            messageRef.child(messageId).setValue(message)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            editTextMessage.setText("");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle failure to send message
                            Log.e("UserChatActivity", "Failed to send message: " + e.getMessage());
                            Toast.makeText(UserchatActivity.this, "Failed to send message", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
