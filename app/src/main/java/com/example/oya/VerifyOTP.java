
package com.example.oya;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.oya.Utility.NetworkChangeListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import android.text.Editable;

public class VerifyOTP extends AppCompatActivity {
    private EditText otpDigit1, otpDigit2, otpDigit3, otpDigit4, otpDigit5, otpDigit6;
    private RelativeLayout verifyOTPBtn;
    private TextView buttonText;
    private LottieAnimationView button_animation;
    private static final int TIMER = 16000;
    private NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private FirebaseAuth mAuth;
    private String verificationId;
    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, intentFilter);
        super.onStart();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }
    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vefify_otp);

        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        otpDigit1 = findViewById(R.id.otpDigit1);
        otpDigit2 = findViewById(R.id.otpDigit2);
        otpDigit3 = findViewById(R.id.otpDigit3);
        otpDigit4 = findViewById(R.id.otpDigit4);
        otpDigit5 = findViewById(R.id.otpDigit5);
        otpDigit6 = findViewById(R.id.otpDigit6);
        buttonText = findViewById(R.id.buttonText);
        button_animation = findViewById(R.id.button_animation);
        verifyOTPBtn = findViewById(R.id.verifyOTPBtn);

        verificationId = getIntent().getStringExtra("verificationId");
        setOTPTextWatchers();
        //setting up text watcher

        verifyOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = getOTPFromFields();
                if (TextUtils.isEmpty(otp) || otp.length() <6){
                    Toast.makeText(VerifyOTP.this, "Please enter the valid OTP", Toast.LENGTH_SHORT).show();
                }else{
                    verifyCode(otp);
                }
                button_animation.setVisibility(View.VISIBLE);
                button_animation.playAnimation();
                buttonText.setVisibility(View.GONE);
                new Handler().postDelayed(() -> resetButton(), TIMER);
            }
        });
    }

    private void setOTPTextWatchers() {
        otpDigit1.addTextChangedListener(new OTPTextWatcher(otpDigit1, null, null));
        otpDigit2.addTextChangedListener(new OTPTextWatcher(otpDigit2, otpDigit1, otpDigit3));
        otpDigit3.addTextChangedListener(new OTPTextWatcher(otpDigit3, otpDigit2, otpDigit4));
        otpDigit4.addTextChangedListener(new OTPTextWatcher(otpDigit4, otpDigit3, otpDigit5));
        otpDigit5.addTextChangedListener(new OTPTextWatcher(otpDigit5, otpDigit4, otpDigit6));
        otpDigit6.addTextChangedListener(new OTPTextWatcher(otpDigit6, otpDigit5, null));
    }
    private class OTPTextWatcher implements TextWatcher{
        private EditText currentField;
        EditText previousField;
        private EditText nextField;
        public OTPTextWatcher(EditText currentField, EditText previousField,EditText nextField ) {
            this.currentField = currentField;
            this.previousField = previousField;
            this.nextField = nextField;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 1) {
                // Move the focus to the next OTP field if a digit is entered
                if (nextField != null) {
                    nextField.requestFocus();
                }
            } else if (s.length() == 0 && previousField != null) {
                //Move the focus (cursor) to the previous OTP field if digit is deleted
                previousField.requestFocus();
        }
        }
    }
    private String getOTPFromFields(){
        StringBuilder otp = new StringBuilder();
        otp.append(otpDigit1.getText().toString());
        otp.append(otpDigit2.getText().toString());
        otp.append(otpDigit3.getText().toString());
        otp.append(otpDigit4.getText().toString());
        otp.append(otpDigit5.getText().toString());
        otp.append(otpDigit6.getText().toString());
        return otp.toString();
    }
    // Button Animation
    private void resetButton() {
        button_animation.pauseAnimation();
        button_animation.setVisibility(View.GONE);
        buttonText.setVisibility(View.VISIBLE);
    }
    private void verifyCode(String otp) {
        if (TextUtils.isEmpty(verificationId)) {
            Toast.makeText(this, "Verification ID is null or empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(otp)) {
            Toast.makeText(this, "Verification code is null or empty", Toast.LENGTH_SHORT).show();
            return;
        }
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user != null){
                        final DatabaseReference mUserDB = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());
                        mUserDB.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.exists()) {
                                    Map<String, Object> userMap = new HashMap<>();
                                    userMap.put("phone", user.getPhoneNumber());
                                    userMap.put("name", user.getPhoneNumber());
                                    mUserDB.updateChildren(userMap)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> innerTask) {
                                                    if (innerTask.isSuccessful()) {
                                                        Intent i = new Intent(VerifyOTP.this, setProfile.class);
                                                        startActivity(i);
                                                        finish();
                                                    } else {
                                                        Toast.makeText(VerifyOTP.this, "Failed to update user information", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                } else {
                                    // Handle the case where currentUser is null
                                    Toast.makeText(VerifyOTP.this, "User is not authenticated", Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
        });
    }
}
