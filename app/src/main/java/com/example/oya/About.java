package com.example.oya;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class About extends AppCompatActivity {
    private WebView wb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //SET APP ORIENTATION TO VERTICAL
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#0045FC"));
        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Changing Title bar Text
        getSupportActionBar().setTitle("About");  // provide compatibility to all the versions

        //Load Oya.online website for about activity
        wb=(WebView)findViewById(R.id.web_view);
        WebSettings webSettings=wb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wb.loadUrl("https://oyaonline.com.ng");

    }
}