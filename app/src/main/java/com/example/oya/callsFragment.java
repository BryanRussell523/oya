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

public class callsFragment extends Fragment {
    ImageButton fab2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.callsfragment, container, false);{
//        return inflater.inflate(R.layout.statusfragment,null);

        //WALLET BUTTON
        fab2 = view.findViewById(R.id.fab2);
        //OPEN OYA PAY
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MAKE ANIMATED SPLASH SCREEN FOR SWITCHING MODES
                Intent intent2 = new Intent(getActivity(), SplashScreenPro.class);
                startActivity(intent2);
            }
        });
        return view;
    }
}
}
