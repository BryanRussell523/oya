package com.example.oya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // on below line we are configuring our window to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        // on below line we are calling handler to run a task
        // for specific time interval
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // on below line we are
                // creating a new intent
                Intent intent = new Intent(MainActivity.this,SignUp.class);
                startActivity(intent);

                // on the below line we are finishing
                // our current activity.
                finish();
            }
        }, 3000);
        //For 4G check
        boolean is4g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();
        //For WiFi Check
        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();


        if (!is4g && !isWifi)
        {
            Toast.makeText(getApplicationContext(),"No Internet Connection", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Connected to Server", Toast.LENGTH_SHORT).show();

        }
    }
}