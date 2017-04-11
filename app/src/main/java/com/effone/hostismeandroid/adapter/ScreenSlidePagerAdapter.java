package com.effone.hostismeandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.effone.hostismeandroid.fragment.HotDishHomePageImageFragment;

/**
 * Created by sarith.vasu on 10-04-2017.
 */

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new HotDishHomePageImageFragment();
    }


    @Override
    public int getCount() {
        return 5;
    }
}
