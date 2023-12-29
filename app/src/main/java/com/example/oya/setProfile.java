package com.example.oya;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.oya.Object.UserObject;

public class setProfile extends AppCompatActivity {
    UserObject userModel;
    EditText usernameInput;
    RelativeLayout saveprofile;
    String phoneNumber;

    LottieAnimationView button_animation;
    TextView buttonText;
    public static final int TIMER = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);
}}