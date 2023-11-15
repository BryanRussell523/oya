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
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.oya.Adapter.UserAdapter;
import com.example.oya.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class selectUser extends AppCompatActivity {
    ImageButton backbuttonofselectuser;
    private RecyclerView usersrecyclerview;
    private UserAdapter userAdapter;
    private List<User> Users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        backbuttonofselectuser = findViewById(R.id.backbuttonofselectuser);

        usersrecyclerview = findViewById(R.id.usersrecyclerview);
        usersrecyclerview.setHasFixedSize(true);
        usersrecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Users = new ArrayList<>();
        readUsers();
        backbuttonofselectuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(selectUser.this,chatViewActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //LOADING USERS FROM DATABASE

    }

    private void readUsers() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
         reference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 Users.clear();
                 for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                     User user = snapshot.getValue(User.class);
                     assert user != null;
                     assert firebaseUser != null;
                     if (!user.getId().equals(firebaseUser.getUid())){
                         Users.add(user);
                     }
                 }
                 userAdapter = new UserAdapter(getApplicationContext(), Users);
                 usersrecyclerview.setAdapter(userAdapter);
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });
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
