package com.example.oya;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

//THIS JAVA CLASS HELPS TO ACCESS ALL THE FRAGMENTS
public class PagerAdapterforpromode extends FragmentPagerAdapter {
    int tabcount;


    public PagerAdapterforpromode(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                return new walletFragment();
            case 1:
                return new prosettingsFragment();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
