package com.example.oya;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.Voice;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class callsFragment extends Fragment {
    ImageView search,Videocall,Voicecall;
    androidx.appcompat.widget.Toolbar toolbar;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.callsfragment, container, false);{

            toolbar = view.findViewById(R.id.toolbar);
            ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
            search = view.findViewById(R.id.search);
            Videocall = view.findViewById(R.id.Videocall);
            Voicecall = view.findViewById(R.id.Voicecall);

            // Get the activity instance
            AppCompatActivity activity = (AppCompatActivity) requireActivity();
            // Set the status bar color to white
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().setStatusBarColor(Color.WHITE);
            }
            recyclerView = view.findViewById(R.id.recyclerView);

            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Search", Toast.LENGTH_SHORT).show();

                }
            });
            Videocall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Video call", Toast.LENGTH_SHORT).show();
                }
            });
            Voicecall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Voice call", Toast.LENGTH_SHORT).show();
                }
            });

        return view;
    }
}

}
