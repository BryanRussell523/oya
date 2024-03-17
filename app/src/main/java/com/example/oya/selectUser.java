package com.example.oya;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oya.Adapter.UserListAdapter;
import com.example.oya.Object.UserObject;
import com.example.oya.Utility.NetworkChangeListener;
import com.example.oya.homeActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.Manifest;
import android.content.pm.PackageManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class selectUser extends AppCompatActivity {
    ImageButton backbuttonofselectuser;
    RelativeLayout newGroup, newContact;
    private NetworkChangeListener networkChangeListener;
    private static final int REQUEST_READ_CONTACTS = 101;
    private List<UserObject> userList = new ArrayList<>();
    private Set<String> contactNames = new HashSet<>();

    private RecyclerView recyclerView;
    private UserListAdapter userListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        //get custom status bar color
        setStatusBarColor(getResources().getColor(android.R.color.white));
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);


        backbuttonofselectuser = findViewById(R.id.backbuttonofselectuser);
        newGroup = findViewById(R.id.newGroup);
        newContact = findViewById(R.id.newContact);

        //GET PERMISSION TO LOAD USERS FROM CONTACT LIST
        getPermissions();

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userListAdapter = new UserListAdapter(userList, this, new UserListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(UserObject user) {
                //TODO
            }
        });
        recyclerView.setAdapter(userListAdapter);

        //Initialize network change listener
        networkChangeListener = new NetworkChangeListener();
        newGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(selectUser.this, "New Chat Group", Toast.LENGTH_SHORT).show();
            }
        });
        newContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(selectUser.this, "New Contact", Toast.LENGTH_SHORT).show();
            }
        });
        backbuttonofselectuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(selectUser.this, homeActivity.class);
                startActivity(intent);
                finish();
            }
        });

//Request contacts permission if not granted already
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACTS);
        } else {
            // Permission already granted, load contacts
            loadContacts();
        }

        // Load users from Realtime Database
        loadUsersFromRealtimeDatabase();
    }
    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(color);
        }
    }
    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
    }

    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // Permission granted, load contacts
                loadContacts();
            } else {
                // Permission denied, show a message or handle it accordingly
                Log.d("Permissions", "Contacts permission denied");
            }
        }
    }
    // Load contacts from device
    private void loadContacts() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contactNames.add(name);
            }
            cursor.close();
        }
        // Update RecyclerView
        userListAdapter.notifyDataSetChanged();
    }

    // Load users from Firebase Realtime Database
    private void loadUsersFromRealtimeDatabase() {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("user");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<UserObject> usersFromRealtimeDatabase = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String uid = userSnapshot.getKey();
                    String phoneNumber = userSnapshot.child("phoneNumber").getValue(String.class);
                    String username = userSnapshot.child("username").getValue(String.class);
                    String imageUri = userSnapshot.child("imageUri").getValue(String.class);
                    String description = userSnapshot.child("description").getValue(String.class);

                    String name = userSnapshot.child("name").getValue(String.class);




                    UserObject user = new UserObject(uid, phoneNumber, username, imageUri, description, name);
                    usersFromRealtimeDatabase.add(user);
                }
                // Merge contacts and users from Realtime Database, removing duplicates
                for (UserObject user : usersFromRealtimeDatabase) {
                    if (!contactNames.contains(user.getName())) {
                        userList.add(user);
                    }
                }
                // Update RecyclerView
                userListAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Toast.makeText(selectUser.this, "Failed to load users from Realtime Database: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("SelectUserActivity", "loadUsersFromRealtimeDatabase: onCancelled", databaseError.toException());
            }
        });
    }
   
}
