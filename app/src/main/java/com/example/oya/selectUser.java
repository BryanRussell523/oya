package com.example.oya;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.Manifest;

public class selectUser extends AppCompatActivity{
    ImageButton backbutton;
    RelativeLayout newGroup, newContact;
    androidx.appcompat.widget.Toolbar toolbar;
    private RecyclerView recyclerView;
    private UserListAdapter userListAdapter;
    private LinearLayoutManager linearLayoutManager;

    private List<UserObject> contactList = new ArrayList<>();

    private static final int REQUEST_READ_CONTACTS = 1;
    private Set<String> deviceContacts = new HashSet<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColorToWhite();
        setContentView(R.layout.activity_selectusers);
        backbutton = findViewById(R.id.backbutton);
        toolbar = findViewById(R.id.toolbar);

        newGroup = findViewById(R.id.newGroup);
        newContact = findViewById(R.id.newContact);

        checkContactsPermission();
        contactList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        userListAdapter = new UserListAdapter(this, contactList);
        recyclerView.setAdapter(userListAdapter);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        newGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(selectUser.this, "New Group", Toast.LENGTH_SHORT).show();
            }
        });
        newContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(selectUser.this, "New contact", Toast.LENGTH_SHORT).show();
            }
        });
        // Fetch device contacts
        fetchDeviceContacts();
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
    private void checkContactsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        } else {
            // Permission is already granted
            loadContacts();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // Call super method
        if (requestCode == REQUEST_READ_CONTACTS) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed to load contacts
                loadContacts();
            } else {
                // Permission denied, show a message or handle accordingly
                Toast.makeText(this, "Permission denied. Cannot access contacts.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void loadContacts() {
        Log.d("selectUser", "Loading contacts...");
        contactList.clear();
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("user");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String uid = userSnapshot.child("uid").getValue(String.class);
                    String phoneNumber = userSnapshot.child("name").getValue(String.class);
                    String phone = userSnapshot.child("phone").getValue(String.class);
                    String username = userSnapshot.child("username").getValue(String.class);
                    String description = userSnapshot.child("description").getValue(String.class);
                    String profileImageUrl = userSnapshot.child("image").getValue(String.class);

                    // Log user details
                    Log.d("FirebaseDataCheck", "User: " + phoneNumber + ", Username: " + username);

                    if (deviceContacts.contains(phoneNumber)) {
                        // Create a new UserObject and add it to the contactList
                        UserObject user = new UserObject(uid, phone, phone, username, description, profileImageUrl);
                        contactList.add(user);
                    }
                }
                // Notify the adapter of the data change
                userListAdapter.notifyDataSetChanged();

                // Log the final contact list size
                Log.d("ContactListSize", "Contact list size: " + contactList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
                Log.e("FirebaseError", "Firebase data retrieval error: " + error.getMessage());
            }
        });
    }
    private void fetchDeviceContacts() {
        Log.d("selectUserCheck", "Fetching device contacts...");

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String phoneNumber = getPhoneNumber(contentResolver,contactId);
                phoneNumber = normalizePhoneNumber(phoneNumber);
                // Log device contact
                Log.d("DeviceContact", "Contact: " + displayName + ", Phone: " + phoneNumber);

                deviceContacts.add(phoneNumber);
            }
            cursor.close();
        }
        // Log the final device contacts set
        Log.d("DeviceContactsSet", "Device contacts: " + deviceContacts.toString());
        Log.d("DeviceContactsSize", "Device contacts size: " + deviceContacts.size());
    }
    private String getPhoneNumber(ContentResolver contentResolver, String contactId) {
        String phoneNumber = "";
        Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",new String[]{contactId},null);
        if (phoneCursor != null && phoneCursor.moveToFirst()) {
            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phoneCursor.close();
        }
        return phoneNumber;
    }
    private String normalizePhoneNumber(String phoneNumber) {
        // Remove all non-numeric characters
        phoneNumber = phoneNumber.replaceAll("[^0-9]", "");

        // Remove leading zeros
        phoneNumber = phoneNumber.replaceAll("^0+", "");

        return phoneNumber;
    }
    //    private String normalizePhoneNumber(String phoneNumber) {
//        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
//        try {
//            Phonenumber.PhoneNumber phone = phoneUtil.parse(phoneNumber, "NG");
//            return phone.toString();
//        } catch (NumberParseException e) {
//            return phoneNumber;
//
//        }
//    }
}

