package com.example.oya;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.oya.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class prosettingsFragment extends Fragment {
    androidx.appcompat.widget.AppCompatButton avatar;
    androidx.appcompat.widget.AppCompatButton account;
    androidx.appcompat.widget.AppCompatButton invite;
    androidx.appcompat.widget.AppCompatButton logout;
    ImageView viewuserimageimageview;
    TextView username;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.prosettingsfragment, container, false);
        //return inflater.inflate(R.layout.settingsfragment,null);

        username = view.findViewById(R.id.username);
        viewuserimageimageview = view.findViewById(R.id.viewuserimageimageview);
        avatar = view.findViewById(R.id.avatar);
        account = view.findViewById(R.id.account);
        invite = view.findViewById(R.id.invite);
        logout = view.findViewById(R.id.logout);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if (user.getImageURL().equals("default")) {
                    viewuserimageimageview.setImageResource(R.drawable.defaultprfilepic);
                } else {
                    Glide.with(prosettingsFragment.this).load(user.getImageURL()).into(viewuserimageimageview);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),UpdateProfile.class);
                startActivity(intent);

            }
        });
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Create Avatar", Toast.LENGTH_SHORT).show();

            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),accountSettings.class);
                startActivity(intent);
            }
        });

        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Invite a friend", Toast.LENGTH_SHORT).show();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();

            }
        });

        return view;

    }

}
