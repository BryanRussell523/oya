package com.example.oya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.example.oya.Utility.NetworkChangeListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.snapshot.BooleanNode;

public class MainActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private FirebaseAuth mAuth;
    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase.getInstance().getReference().keepSynced(true);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(() -> {
            // Check if the user is already logged in
            if (isUserLoggedIn()) {
                // User is logged in, navigate to the home activity
                Intent intent = new Intent(MainActivity.this, homeActivity.class);
                startActivity(intent);
            } else {
                // User is not logged in, navigate to the sign-up activity
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
            finish(); // Finish the splash screen activity
        }, SPLASH_DISPLAY_LENGTH);

        //CHECK NETWORK STATE AND DISPLAY MESSAGE
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            //DISPLAY CONNECTED INFO ON SNACKBAR
            Snackbar.make(findViewById(R.id.layout), "Connected to the server", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(findViewById(R.id.layout), "No internet connection", Snackbar.LENGTH_SHORT).show();
        }

        }
    //Monitor network change
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
    // Check if the user is logged in
    private boolean isUserLoggedIn() {
        FirebaseUser user = mAuth.getCurrentUser();
        return user != null;
    }

}
