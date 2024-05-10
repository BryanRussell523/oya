package com.example.oya;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.airbnb.lottie.LottieAnimationView;
import com.example.oya.Utility.NetworkChangeListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class SignUp extends AppCompatActivity {
    private EditText phonenumber;
    private FirebaseAuth mAuth;

    private RelativeLayout getOTP;
    private LottieAnimationView button_animation;
    TextView buttonText;
    private static final int TIMER = 16000;
    private CountryCodePicker countryCodePicker;
    private String verificationId;
    private NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack;
    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, intentFilter);
        super.onStart();
    }
    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        phonenumber = findViewById(R.id.phonenumber);
        getOTP = findViewById(R.id.getOTP);
        button_animation = findViewById(R.id.button_animation);
        buttonText = findViewById(R.id.buttonText);
        countryCodePicker = findViewById(R.id.countryCodePicker);

        mAuth = FirebaseAuth.getInstance();

        // Remove first 0 from phone number entry
        phonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 1 && s.toString().trim().equals("0")) {
                    phonenumber.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        getOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String countryCode = countryCodePicker.getSelectedCountryCode();
                String phoneNumber = phonenumber.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(SignUp.this, "Enter a valid phone number", Toast.LENGTH_SHORT).show();
                } else {
                    sendVerificationCode("+" + countryCode + phoneNumber); // Use the concatenated phone number string here
                }
                // Add animation and reset button logic
                button_animation.setVisibility(View.VISIBLE);
                button_animation.playAnimation();
                buttonText.setVisibility(View.GONE);
                new Handler().postDelayed(() -> resetButton(), TIMER);
            }
        });
        mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verificationId, forceResendingToken);
                Intent intent = new Intent(SignUp.this, VerifyOTP.class);
                intent.putExtra("verificationId", verificationId);
                startActivity(intent);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


            }
            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
    }
//    private void userIsLoggedIn() {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            // Check if the user has set up their profile
//            DatabaseReference mUserDB = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());
//            mUserDB.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()) {
//                        // Profile is set, navigate to the home page
//                        startActivity(new Intent(getApplicationContext(), homeActivity.class));
//                        finish();
//                    } else {
//                        // Profile is not set, proceed to the setProfile activity
//                        startActivity(new Intent(getApplicationContext(), setProfile.class));
//                        finish();
//                    }
//                }
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    // Handle database error if needed
//                }
//            });
//        }
//    }
    //Button Animation
    private void resetButton() {
        button_animation.pauseAnimation();
        button_animation.setVisibility(View.GONE);
        buttonText.setVisibility(View.VISIBLE);
    }
    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallBack)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }
}