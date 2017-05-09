package com.effone.hostismeandroid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.app.AppController;

import com.effone.hostismeandroid.common.UpdateableFragment;
import com.effone.hostismeandroid.fragment.MenuViewFragment;
import com.effone.hostismeandroid.model.HISMenuItem;
import com.effone.hostismeandroid.model.HomePageDish;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HISMenuPageAdapter extends FragmentPagerAdapter {


    private HashMap<String ,HashMap<String ,List<HISMenuItem>>> mMenuHashMap;



    public HISMenuPageAdapter(FragmentManager fm, HashMap<String, HashMap<String, List<HISMenuItem>>> mFullMenu) {
        super(fm);

        this.mMenuHashMap = mFullMenu;
    }






    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mMenuHashMap.size();
    }

    @Override
    public Fragment getItem(int position) {
        String heading = (String) (mMenuHashMap.keySet().toArray())[ position ];
        final HashMap<String,List<HISMenuItem>> hisMenuItems = mMenuHashMap.get( heading );
        MenuViewFragment menuViewFragment = new MenuViewFragment();
        menuViewFragment.setHeading(heading);
        menuViewFragment.setValues(hisMenuItems);

        return menuViewFragment;

    }
    public void update() {

        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (object instanceof UpdateableFragment) {
            ((UpdateableFragment) object).update();
        }
        return super.getItemPosition(object);
    }
}

