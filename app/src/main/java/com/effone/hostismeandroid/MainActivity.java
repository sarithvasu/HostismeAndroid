package com.effone.hostismeandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.effone.hostismeandroid.activity.Book_a_tableActivity;
import com.effone.hostismeandroid.activity.MenuActivity;
import com.effone.hostismeandroid.activity.My_BookingActivity;
import com.effone.hostismeandroid.activity.RestaurantListAcitivity;
import com.effone.hostismeandroid.activity.Search_ItemActivity;
import com.effone.hostismeandroid.activity.Service_RequestActivity;
import com.effone.hostismeandroid.activity.View_Pay_BillActivity;
import com.effone.hostismeandroid.adapter.ScreenSlidePagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private AutoScrollViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;
    private static final int NUM_PAGES = 5;
    private ArrayList<Integer> ids;
    private TextView mTvRestaurantList, mTvMenu, mTvBook_a_table, mTvService_Request, mTvViewPay, mTvBooking_History;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.home);
       /* int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView abTitle = (TextView) findViewById(titleId);
        abTitle.setTextColor(getResources().getColor(R.color.white));

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        abTitle.setGravity(Gravity.CENTER);
        abTitle.setWidth(metrics.widthPixels);
        getActionBar().setTitle("I am center now");*/
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Search_ItemActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        init(drawer);
    }

    private void init(View N) {
        ids = new ArrayList<Integer>(Arrays.asList(R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d));
        mPager = (AutoScrollViewPager) findViewById(R.id.view_pager);

        mPager.setAdapter(new CustomPagerAdapter(this));
        mPager.animate();
        mPager.setInterval(2000);
        mPager.startAutoScroll();
        mPager.setBorderAnimation(true);
        mPager.getSlideBorderMode();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mPager, true);
        declerations();
    }

    private void declerations() {
        mTvRestaurantList = (TextView) findViewById(R.id.btn_res_list);
        mTvBook_a_table = (TextView) findViewById(R.id.btn_book_table);
        mTvMenu = (TextView) findViewById(R.id.btn_res_menu);
        mTvService_Request = (TextView) findViewById(R.id.btn_appointments);
        mTvViewPay = (TextView) findViewById(R.id.btn_view_pay);
        mTvBooking_History = (TextView) findViewById(R.id.btn_my_booking);

        mTvBooking_History.setOnClickListener(this);
        mTvViewPay.setOnClickListener(this);
        mTvRestaurantList.setOnClickListener(this);
        mTvBook_a_table.setOnClickListener(this);
        mTvMenu.setOnClickListener(this);
        mTvService_Request.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_appointments:
                intent = new Intent(this, Service_RequestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_res_list:
                intent = new Intent(this, RestaurantListAcitivity.class);
                startActivity(intent);
                break;
            case R.id.btn_res_menu:
                intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_book_table:
                intent = new Intent(this, Book_a_tableActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_view_pay:
                intent = new Intent(this, View_Pay_BillActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_my_booking:
                intent = new Intent(this, My_BookingActivity.class);
                startActivity(intent);
                break;
        }
    }

    private class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return ids.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.fragment_hot_dish_home_page_image, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_dish);
            imageView.setImageResource(ids.get(position));

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
