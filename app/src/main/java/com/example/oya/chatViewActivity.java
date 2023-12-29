package com.example.oya;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.oya.Utility.NetworkChangeListener;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class chatViewActivity extends AppCompatActivity {
    TabLayout tablayout;
    TabItem chatstab, callstab, statustab, settingstab;
    ViewPager fragmentcontainer;
    ImageButton fab;
    PagerAdapter pagerAdapter;
    androidx.appcompat.widget.Toolbar toolbar;
    FirebaseUser firebaseUser;
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
        setContentView(R.layout.activity_chat);


        tablayout = findViewById(R.id.tablayout);
        chatstab = findViewById(R.id.chatstab);
        callstab = findViewById(R.id.callstab);
        statustab = findViewById(R.id.statustab);
        settingstab = findViewById(R.id.settingstab);




        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        fragmentcontainer = findViewById(R.id.fragmentcontainer);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
            return true;
        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();
        if (itemId == R.id.quickcamera) {
            Toast.makeText(this, "Camera", Toast.LENGTH_SHORT).show();

            return true;
        }
        if (itemId == R.id.search_bar) {
            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (itemId == R.id.groupchat) {
            Toast.makeText(this, "Group chat", Toast.LENGTH_SHORT).show();

            return true;
        }
        return false;
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




