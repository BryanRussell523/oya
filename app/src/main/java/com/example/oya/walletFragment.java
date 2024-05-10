package com.example.oya;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class walletFragment extends Fragment {
    // Variable to store the original status bar color
    private int originalStatusBarColor;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.walletfragment, container, false);
        // Store the original status bar color
        originalStatusBarColor = getActivity().getWindow().getStatusBarColor();

        // Check if the Android version is Lollipop or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Set the status bar color to blue
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.blue));
        }
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Revert the status bar color to its original state when the fragment is destroyed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(originalStatusBarColor);
        }
    }
}
