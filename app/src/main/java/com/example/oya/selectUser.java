package com.example.oya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.oya.Adapter.UserListAdapter;
import com.example.oya.Object.UserObject;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

public class selectUser extends AppCompatActivity implements UserListAdapter.OnUserClickListener {
    ImageButton backbuttonofselectuser;
    //INITAILIZING RECYCLER VIEW
    private RecyclerView usersrecyclerview;
    private RecyclerView.Adapter mUserListAdapter;
    private RecyclerView.LayoutManager mUserListLayoutManager;
    ArrayList<UserObject> userList, contactList;
    RelativeLayout newGroup, newContact;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        FirebaseApp.initializeApp(this);



        backbuttonofselectuser = findViewById(R.id.backbuttonofselectuser);
        usersrecyclerview = findViewById(R.id.usersrecyclerview);
        usersrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        contactList = new ArrayList<>();
        userList = new ArrayList<>();
        initializeRecyclerView();
        getContactList();
        newGroup = findViewById(R.id.newGroup);
        newContact = findViewById(R.id.newContact);

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
    }
    //GETTING CONTACTS INFORMATION
    private void getContactList() {
        String ISOPrefix = getCountyISO();
        // String ISOPrefix = getCountryISO();
        HashSet<String> normalizedPhoneNumbers = new HashSet<String>();
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            //normalizing phone contacts
            phone = phone.replaceAll("\\s+", "");
            phone = phone.replaceAll("-", "");
            phone = phone.replaceAll("\\(", "");
            phone = phone.replaceAll("\\)", "");
            if (!String.valueOf(phone.charAt(0)).equals("+")) {
                phone = ISOPrefix + phone;
            }
            if (!normalizedPhoneNumbers.contains(phone)) {
                String imageUrl = "";
                UserObject mContact = new UserObject("", name, phone, imageUrl);
                contactList.add(mContact);
                mUserListAdapter.notifyDataSetChanged();
                normalizedPhoneNumbers.add(phone);
                getUserDetails(mContact);
            }
        }
        usersrecyclerview.setAdapter(mUserListAdapter);
    }
    private void getUserDetails(UserObject mContact) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("user");
        Query query = databaseReference.orderByChild("phone").equalTo(mContact.getPhone());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String phone = "", name = "",imageUri="";
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        if (childSnapshot.child("phone").getValue() != null)
                            phone = childSnapshot.child("phone").getValue().toString();
                        if (childSnapshot.child("name").getValue() != null)
                            name = childSnapshot.child("name").getValue().toString();
                        if (childSnapshot.child("imageUri").getValue()!=null)
                            imageUri = childSnapshot.child("imageUri").getValue().toString();

                        UserObject mUser = new UserObject(childSnapshot.getKey(), name, phone,imageUri);
                        //GET USER NAME OF USERS(CONTACT LIST)
                        if (name.equals(phone))
                            for (UserObject mContactIterator : contactList) {
                                if (mContactIterator.getPhone().equals(mUser.getPhone())) {
                                    mUser.setName(mContactIterator.getName());
                                }
                            }
                        userList.add(mUser);
                        mUserListAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    //Use ISO for contacts
    private String getCountyISO() {
        String iso = null;
        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        if (telephonyManager.getNetworkCountryIso() != null)
            if (!telephonyManager.getNetworkCountryIso().toString().equals(""))
                iso = telephonyManager.getNetworkCountryIso().toString();

        return CountryToPhonePrefix.getPhone(iso);
    }
    // Set up the click listener for the UserItem
    @Override
    public void onUserClick(UserObject user) {
        Intent intent = new Intent(selectUser.this, UserchatActivity.class);
        // Pass user data to the ChatActivity
        intent.putExtra("currentUserId", getCurrentUserId());
        intent.putExtra("otherUserId", user.getUid());
        intent.putExtra("otherUserName", user.getName());
        intent.putExtra("otherUserImageUri", user.getImageUri());
        startActivity(intent);
    }
    private String getCurrentUserId() {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                return currentUser.getUid();
            } else {
                // Handle the case where the current user is null
                return null;
            }
        }
    private void initializeRecyclerView() {
        usersrecyclerview = findViewById(R.id.usersrecyclerview);
        usersrecyclerview.setNestedScrollingEnabled(false);
        usersrecyclerview.setHasFixedSize(false);
        mUserListLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        usersrecyclerview.setLayoutManager(mUserListLayoutManager);
        mUserListAdapter = new UserListAdapter(this,userList, this);

    }
}
