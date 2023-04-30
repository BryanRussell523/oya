package com.example.oya;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class CallsActivity extends AppCompatActivity {
    TextView story,calls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calls);
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
        //Changing Title bar Text
        getSupportActionBar().setTitle("Oya/Calls");  // provide compatibility to all the versions
        //Animated Activity Transition Button(Text View)
        TextView chat = findViewById(R.id.chat);
        TextView story = findViewById(R.id.story);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainPageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),StoryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);


            }
        });

        Button sharedata = (Button) findViewById(R.id.sharedata);
        sharedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();


            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.callsmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.snapstoryicon:
                //Open Camera
                Toast.makeText(this, "Open Camera", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.search:
                //Open Camera
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.clearcall:
                //Open Camera
                Toast.makeText(this, "Clear Call Log", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.settings:
                //ADD INTENT TO LOAD SETTINGS ACTIVITY
                Intent settingsintent = new Intent(CallsActivity.this, Settings.class);
                startActivity(settingsintent);
                return true;
            case R.id.logout:
                //LOGOUT FUNCTION
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }}

}