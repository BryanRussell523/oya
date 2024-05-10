package com.example.oya;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.oya.Utility.NetworkChangeListener;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import android.Manifest;
public class homeActivity extends AppCompatActivity {
    ImageView fab,calculator,calendar,notes;
    TabLayout tablayout;
    TabItem chatstab, callstab, wallettab, settingstab;
    ViewPager fragmentcontainer;
    PagerAdapter pagerAdapter;
    FirebaseUser firebaseUser;
    boolean isOriginalImage = true;
    DatabaseReference reference;
    LinearLayout layout;
    //NETWORK CHANGE LISTENER
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //Monitor network change
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,intentFilter);
        super.onStart();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FirebaseApp.initializeApp(this);

        tablayout = findViewById(R.id.tablayout);
        chatstab = findViewById(R.id.chatstab);
        callstab = findViewById(R.id.callstab);
        wallettab = findViewById(R.id.wallettab);
        settingstab = findViewById(R.id.settingstab);

        calendar = findViewById(R.id.calendar);
        calculator = findViewById(R.id.calculator);
        notes = findViewById(R.id.notes);
        fab = findViewById(R.id.fab);



        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //GET PERMISSION TO LOAD USERS FROM CONTACT LIST
        //checkPermissions();
        getPermissions();
        //get custom status bar color
        setStatusBarColor(getResources().getColor(android.R.color.white));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the visibility of the calendar, notes, and calculator buttons
                if(calendar.getVisibility() == View.VISIBLE) {
                    calendar.setVisibility(View.GONE);
                    notes.setVisibility(View.GONE);
                    calculator.setVisibility(View.GONE);
                    fab.setImageResource(R.drawable.tools); // Change to the open icon
                } else {
                    calendar.setVisibility(View.VISIBLE);
                    notes.setVisibility(View.VISIBLE);
                    calculator.setVisibility(View.VISIBLE);
                    fab.setImageResource(R.drawable.close); // Change to the close icon
                }
            }
        });
        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the image resource of the FAB to the calculator image
                fab.setImageResource(R.drawable.filledcalculator);

                // Hide other buttons if they are visible
                if (calendar.getVisibility() == View.VISIBLE) {
                    calendar.setVisibility(View.GONE);
                    notes.setVisibility(View.GONE);
                    calculator.setVisibility(View.GONE);
                } else {
                    // Show other buttons
                    calendar.setVisibility(View.VISIBLE);
                    notes.setVisibility(View.VISIBLE);
                    calculator.setVisibility(View.VISIBLE);
                }
                // Start the Calculator activity
                Intent intent = new Intent(homeActivity.this, CalculatorActivity.class);
                startActivity(intent);
            }
        });
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the image resource of the FAB to the calculator image
                fab.setImageResource(R.drawable.filledcalendar);

                // Hide other buttons if they are visible
                if (calendar.getVisibility() == View.VISIBLE) {
                    calendar.setVisibility(View.GONE);
                    notes.setVisibility(View.GONE);
                    calculator.setVisibility(View.GONE);
                } else {
                    // Show other buttons
                    calendar.setVisibility(View.VISIBLE);
                    notes.setVisibility(View.VISIBLE);
                    calculator.setVisibility(View.VISIBLE);
                }
                // Start the Calendar activity
                Intent intent = new Intent(homeActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the image resource of the FAB to the calculator image
                fab.setImageResource(R.drawable.fillednote);
                // Hide other buttons if they are visible
                if (calendar.getVisibility() == View.VISIBLE) {
                    calendar.setVisibility(View.GONE);
                    notes.setVisibility(View.GONE);
                    calculator.setVisibility(View.GONE);
                } else {
                    // Show other buttons
                    calendar.setVisibility(View.VISIBLE);
                    notes.setVisibility(View.VISIBLE);
                    calculator.setVisibility(View.VISIBLE);
                }
                // Start the Notes activity
                Intent intent = new Intent(homeActivity.this, NotesActivity.class);
                startActivity(intent);
            }
        });

        fragmentcontainer = findViewById(R.id.fragmentcontainer);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tablayout.getTabCount());
        fragmentcontainer.setAdapter(pagerAdapter);

        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                fragmentcontainer.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 0 || tab.getPosition() == 1 || tab.getPosition() == 2 || tab.getPosition() == 3) {
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        fragmentcontainer.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
    }


    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS,Manifest.permission.READ_CONTACTS},1);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Restore status bar color to the default color
        setStatusBarColor(getResources().getColor(R.color.lightblue));
    }

    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(color);
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        super.onBackPressed();
    }
    //Monitor network change
    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}