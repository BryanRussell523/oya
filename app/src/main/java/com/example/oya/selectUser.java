package com.example.oya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.oya.Adapter.UserListAdapter;
import com.example.oya.Object.UserObject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

public class selectUser extends AppCompatActivity{
    ImageButton backbuttonofselectuser;
    //INITAILIZING RECYCLER VIEW
    private RecyclerView usersrecyclerview;
    private RecyclerView.Adapter mUserListAdapter;
    private RecyclerView.LayoutManager mUserListLayoutManager;
    ArrayList<UserObject> userList, contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        backbuttonofselectuser = findViewById(R.id.backbuttonofselectuser);
        usersrecyclerview = findViewById(R.id.usersrecyclerview);
        usersrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        contactList= new ArrayList<>();
        userList= new ArrayList<>();
        initializeRecyclerView();
        getContactList();

        backbuttonofselectuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(selectUser.this, chatViewActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //GETTING CONTACTS INFORMATION
    private void getContactList(){
        String ISOPrefix = getCountyISO();
        // String ISOPrefix = getCountryISO();
        HashSet<String> normalizedPhoneNumbers = new HashSet<String>();
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while(phones.moveToNext()){
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            //normalizing phone contacts
            phone = phone.replace(" ","");
            phone = phone.replace("-","");
            phone = phone.replace("(","");
            phone = phone.replace(")","");
            if(!String.valueOf(phone.charAt(0)).equals("+"))
                phone = ISOPrefix + phone;
            if (!normalizedPhoneNumbers.contains(phone)) {
                UserObject mContact = new UserObject("",name, phone);
                contactList.add(mContact);
                mUserListAdapter.notifyDataSetChanged();
                normalizedPhoneNumbers.add(phone);
                getUserDetails(mContact);
            }
        }
    }
    private void getUserDetails(UserObject mContact) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("user");
        Query query = databaseReference.orderByChild("phone").equalTo(mContact.getPhone());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String phone ="", name="";
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()){
                        if(childSnapshot.child("phone").getValue()!=null)
                            phone = childSnapshot.child("phone").getValue().toString();
                        if (childSnapshot.child("name").getValue()!=null)
                            name = childSnapshot.child("name").getValue().toString();
                        UserObject mUser = new UserObject(childSnapshot.getKey(),name, phone);
                        //GET USER NAME OF USERS(CONTACT LIST)
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
    //Use ISO for contacts
    private String getCountyISO(){
        String iso = null;
        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        if(telephonyManager.getNetworkCountryIso()!= null)
            if(!telephonyManager.getNetworkCountryIso().toString().equals(""))
                iso = telephonyManager.getNetworkCountryIso().toString();

        return CountryToPhonePrefix.getPhone(iso);
    }
    private void initializeRecyclerView() {
        usersrecyclerview= findViewById(R.id.usersrecyclerview);
        usersrecyclerview.setNestedScrollingEnabled(false);
        usersrecyclerview.setHasFixedSize(false);
        mUserListLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayout.VERTICAL, false);
        usersrecyclerview.setLayoutManager(mUserListLayoutManager);
        mUserListAdapter = new UserListAdapter(userList);
        usersrecyclerview.setAdapter(mUserListAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuforselectuser, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();
        if (itemId == R.id.search_bar) {
            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (itemId == R.id.invite) {
            Toast.makeText(this, "Invite a friend", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }
}
