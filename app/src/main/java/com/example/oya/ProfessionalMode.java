package com.example.oya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.oya.Utility.NetworkChangeListener;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfessionalMode extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    TabLayout tablayout;
    TabItem wallettab, settingstab;
    ViewPager fragmentcontainer;
    PagerAdapterforpromode pagerAdapter;
    androidx.appcompat.widget.Toolbar toolbar;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    LinearLayout layout;
    TextView myapptext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_mode);
        //Load Fire base data on time
        FirebaseDatabase.getInstance().getReference().keepSynced(true);
        //display custom textview and menu
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView myapptext = findViewById(R.id.myapptext);
        myapptext.setText("Professional Mode");


        tablayout = findViewById(R.id.tablayout);
        wallettab = findViewById(R.id.wallettab);
        settingstab = findViewById(R.id.settingstab);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        fragmentcontainer = findViewById(R.id.fragmentcontainer);
        pagerAdapter = new PagerAdapterforpromode(getSupportFragmentManager(), tablayout.getTabCount());
        fragmentcontainer.setAdapter(pagerAdapter);
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentcontainer.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0 || tab.getPosition() ==1){
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
        getMenuInflater().inflate(R.menu.menuforpro,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.switchtochat){
            //Create animated splashscreen for switching modes(Chat Mode and Professional Mode)
            Intent intent = new Intent(ProfessionalMode.this,chatViewActivity.class);
            startActivity(intent);
            finish();
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