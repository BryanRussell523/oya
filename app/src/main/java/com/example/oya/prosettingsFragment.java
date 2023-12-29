package com.example.oya;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class prosettingsFragment extends Fragment {
    androidx.appcompat.widget.AppCompatButton avatar;
    androidx.appcompat.widget.AppCompatButton account;
    androidx.appcompat.widget.AppCompatButton invite;
    androidx.appcompat.widget.AppCompatButton logout;
    ImageView viewuserimageimageview;
    TextView username;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    ImageButton fab2;
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
        fab2 = view.findViewById(R.id.fab2);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

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
                startActivity(new Intent(getActivity(), SignUp.class));
                getActivity().finish();

            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), chatViewActivity.class);
                startActivity(intent);

            }
        });


        return view;

    }

}
