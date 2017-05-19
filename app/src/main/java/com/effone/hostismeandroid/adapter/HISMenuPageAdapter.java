package com.effone.hostismeandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;


import com.effone.hostismeandroid.fragment.MenuViewFragment;
import com.effone.hostismeandroid.model.Items;

import java.util.HashMap;

public class HISMenuPageAdapter extends FragmentPagerAdapter  {


    private HashMap<String ,Items[]> mFullmenu;
    private ViewPager mViewPager;


    public HISMenuPageAdapter(FragmentManager fm, HashMap<String ,Items[]> mFullmenu) {
        super(fm);

        this.mFullmenu = mFullmenu;
    }






    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mFullmenu.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        mViewPager=(ViewPager)container;
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        String heading = (String) (mFullmenu.keySet().toArray())[ position ];
        String preheading="";;
        String postheading="";
        if(position!=0&&position<(mFullmenu.size()-1)){
             preheading=(String) (mFullmenu.keySet().toArray())[ position-1 ];
             postheading=(String) (mFullmenu.keySet().toArray())[ position+1 ];
        }
        if(position==0){
            postheading=(String) (mFullmenu.keySet().toArray())[ position+1 ];
        }
        if(position==(mFullmenu.size()-1)){
            preheading=(String) (mFullmenu.keySet().toArray())[ position-1 ];
        }
        Items[] hisMenuItems = mFullmenu.get( heading );
        MenuViewFragment menuViewFragment = new MenuViewFragment();
        menuViewFragment.setParentView(mViewPager);
        menuViewFragment.setHeading(preheading,postheading,heading,position);
        menuViewFragment.setValues(hisMenuItems);

        return menuViewFragment;

    }

    private void arrowShowingMethods(int position) {

    }

    public void update() {

        notifyDataSetChanged();
    }


    @Override
    public int getItemPosition(Object object) {

        return super.getItemPosition(object);
    }

}

