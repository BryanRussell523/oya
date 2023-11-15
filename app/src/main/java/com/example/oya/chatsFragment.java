package com.example.oya;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oya.Adapter.UserAdapter;
import com.example.oya.Model.Chat;
import com.example.oya.Model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class chatsFragment extends Fragment {
    ImageButton fab;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mUsers;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    private List<String> userList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chatsfragment, container, false);
        //return inflater.inflate(R.layout.chatsfragment, null);
        fab = view.findViewById(R.id.fab);
        //Display users with chat history
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        userList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //to empty the list before adding new user items to it
                userList.clear();
                //For Loop to keep displaying users on list
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //call chat class to get the list of users with chat history
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getSender().equals(firebaseUser.getUid())) {
                        userList.add(chat.getReceiver());
                    }
                    if (chat.getReceiver().equals(firebaseUser.getUid())) {
                        userList.add(chat.getSender());
                    }
                }
                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//OPEN ALL USERS LIST ACTIVITY
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), selectUser.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void readChats() {
        mUsers = new ArrayList<>();
        //Go inside "Users" table in firebase database and fetch users with chat history
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //to empty the list before adding new user items to it
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    //display 1 user from chats
                    for (String id : userList) {
                        if (user.getId().equals(id)) {
                            if (mUsers.size() != 0) {
                                ListIterator<User> listIteratorUser = mUsers.listIterator();
                                while (listIteratorUser.hasNext()) {
                                    User user1 = listIteratorUser.next();
                                    if (!user.getId().equals(user1.getId())) {
                                        listIteratorUser.add(user);
                                        break;
                                    }
                                }
                            } else {
                                mUsers.add(user);
                            }
                        }

                    }
                }
                userAdapter = new UserAdapter(getContext(), mUsers);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}



