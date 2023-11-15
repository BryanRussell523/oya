package com.example.oya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

import com.example.oya.Utility.NetworkChangeListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

public class SplashScreenPro extends AppCompatActivity {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_pro);
        FirebaseDatabase.getInstance().getReference().keepSynced(true);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashScreenPro.this,ProfessionalMode.class);
                SplashScreenPro.this.startActivity(mainIntent);
                SplashScreenPro.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
        //CHECK NETWORK STATE AND DISPLAY MESSAGE
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            //DISPLAY CONNECTED INFO ON SNACKBAR
            Snackbar.make(findViewById(R.id.layout),"Switching to Oya Pay...",Snackbar.LENGTH_SHORT).show();
        }else{
            Snackbar.make(findViewById(R.id.layout),"No internet connection",Snackbar.LENGTH_SHORT).show();
        }

    }
    //Monitor network change
    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}