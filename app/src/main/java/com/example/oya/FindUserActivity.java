package com.example.oya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.telephony.TelephonyManager;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FindUserActivity extends AppCompatActivity {
    private RecyclerView mUserList;
    private RecyclerView.Adapter mUserListAdapter;
    private RecyclerView.LayoutManager mUserListLayoutManager;
    ArrayList<UserObject> userList, contactList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);
        //SET APP ORIENTATION TO VERTICAL
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Changing Title bar Text
        getSupportActionBar().setTitle("Select Contact For Chat");  // provide compatibility to all the versions
       // GETTING CONTACTS
        contactList = new ArrayList<>();
        userList = new ArrayList<>();
        initializeRecyclerView();
        getContactList();

    }
    private void getContactList(){
        String ISOPrefix = getCountryISO();
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        while(phones.moveToNext()){
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            //Normalizing the Contacts Number
            phone = phone.replace("","");
            phone = phone.replace("-","");
            phone = phone.replace("(","");
            phone = phone.replace(")","");
            if(!String.valueOf(phone.charAt(0)).equals("+"))
                phone = ISOPrefix + phone;
            UserObject mContact = new UserObject("",name,phone);
            contactList.add(mContact);

            //Function to get data from database:
            getUserDetails(mContact);
        }
    }

    private void getUserDetails(UserObject mContact) {
        DatabaseReference mUserDB = FirebaseDatabase.getInstance().getReference().child("user");
        Query query = mUserDB.orderByChild("phone").equalTo(mContact.getPhone());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String phone = "",
                            name = "";
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()){
                         if(childSnapshot.child("phone").getValue() !=null)
                             phone = childSnapshot.child("phone").getValue().toString();
                        if(childSnapshot.child("name").getValue() !=null)
                            name = childSnapshot.child("name").getValue().toString();

                        UserObject mUser = new UserObject(childSnapshot.getKey(),name,phone);
                        //GETTING NAME OF USER ON CONTACT LIST AND OYA
                        if(name.equals(phone))
                            for(UserObject mContactIterator : contactList){
                                if(mContactIterator.getPhone().equals(mUser.getPhone())){
                                    mUser.setName(mContactIterator.getName());
                                }
                            }

                        userList.add(mUser);
                        mUserListAdapter.notifyDataSetChanged();
                        return;
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //FUNCTION BELOW IS TO MAKE PHONE NUMBER IDENTIFICATION EASIER FOR USERS FROM DIFFERENT COUNTRIES:
    //JAVA CLASS (CountryToPhonePrefix)

     private String getCountryISO(){
        String iso =  null;
         TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);
         if(telephonyManager.getNetworkCountryIso()!=null)
             if (!telephonyManager.getNetworkCountryIso(). toString().equals(""))
                 iso = telephonyManager.getNetworkCountryIso(). toString();
         //Converting iso into phone indicator:
        return CountryToPhonePrefix.getPhone(iso);
     }
     //CONTACT LIST CONTAINER
    private void initializeRecyclerView() {
        mUserList = findViewById(R.id.userList);
        mUserList.setNestedScrollingEnabled(false);
        mUserList.setHasFixedSize(false);
        mUserListLayoutManager  = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        mUserList.setLayoutManager(mUserListLayoutManager);
        mUserListAdapter = new UserListAdapter(userList);
        mUserList.setAdapter(mUserListAdapter);
    }
}