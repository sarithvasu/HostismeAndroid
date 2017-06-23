package com.effone.hostismeandroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.effone.hostismeandroid.activity.Book_a_tableActivity;
import com.effone.hostismeandroid.activity.MenuActivity;
import com.effone.hostismeandroid.activity.My_BookingActivity;
import com.effone.hostismeandroid.activity.RestaurantListAcitivity;
import com.effone.hostismeandroid.activity.Service_RequestActivity;
import com.effone.hostismeandroid.activity.View_Pay_BillActivity;
import com.effone.hostismeandroid.adapter.ScreenSlidePagerAdapter;
import com.effone.hostismeandroid.app.AppController;
import com.effone.hostismeandroid.common.AppPreferences;
import com.effone.hostismeandroid.db.SqlOperations;
import com.effone.hostismeandroid.model.HomePageDish;
import com.effone.hostismeandroid.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import static com.effone.hostismeandroid.common.URL.dish_images_url;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private AutoScrollViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;
    private static final int NUM_PAGES = 5;
    ArrayList<Integer> ids;
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView mTvRestaurantList, mTvMenu, mTvBook_a_table, mTvService_Request, mTvViewPay, mTvBooking_History;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private ProgressDialog pDialog;
    private CustomPagerAdapter mCustomPagerAdapter;
    private AppPreferences appPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
     /*   getSupportActionBar().setTitle(R.string.home);*/
        setToolbar();
       /* ArrayList<Restaurant> restaurants=new ArrayList<Restaurant>();
        Restaurant restaurant1=new Restaurant(101,1001,"Restaurant name One", "39,Lime St", "Sydney NSW 2000", "Australia","2055");
        Restaurant restaurant2=new Restaurant(102,1001,"Restaurant name Two", "1 maquarie St", "Sydney NSW 2000", "Australia","2055");
        Restaurant restaurant3=new Restaurant(103,1001,"Restaurant name Three", "corner Pitt + Hunder Street", "Sydney NSW 2000", "Australia","2055");
        restaurants.add(restaurant1);
        restaurants.add(restaurant2);
        restaurants.add(restaurant3);
        Gson gson=new Gson();
        String jspn= gson.toJson(restaurants);*/
       /* int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView abTitle = (TextView) findViewById(titleId);
        abTitle.setTextColor(getResources().getColor(R.color.white));

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        abTitle.setGravity(Gravity.CENTER);
        abTitle.setWidth(metrics.widthPixels);
        getActionBar().setTitle("I am center now");*/
       /* toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Search_ItemActivity.class);
                startActivity(intent);
            }
        });
*/
        appPreferences = new AppPreferences(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        init(drawer);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ///as off now i am setting the values
        appPreferences.setDEVICE_ID(Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID));
        if (!Util.Operations.isOnline(this)) {
            Util.createNetErrorDialog(this);
        }
    }

    private void setToolbar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        LinearLayout customView = (LinearLayout)
                LayoutInflater.from(this).inflate(R.layout.title_custom,
                        null);
        ActionBar.LayoutParams params = new
                ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        getSupportActionBar().setCustomView(customView, params);
        TextView cust_ttile = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.tv_custom_titile);
        cust_ttile.setText(getString(R.string.home));
        TextView cust_sub_ttile = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.tv_custom_sub_titile);
        cust_sub_ttile.setText("Syndey");


    }

    private void init(View N) {
        ids = new ArrayList<Integer>(Arrays.asList(R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d));
        mPager = (AutoScrollViewPager) findViewById(R.id.view_pager);
        //dummyUrlCode();
        mPager.animate();
        mPager.setInterval(2000);
        mPager.startAutoScroll();
        mPager.setBorderAnimation(true);
        mPager.getSlideBorderMode();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mPager, true);

        declerations();
    }

    @Override
    protected void onResume() {
        if(mTvBook_a_table!=null&&appPreferences!=null&&appPreferences.getTABLE_NAME()==0||appPreferences.getTABLE_NAME()==9999){
            mTvBook_a_table.setText(getString(R.string.book_a_table));
        }
        else{
            mTvBook_a_table.setText(getString(R.string.move_a_table));
        }
        super.onResume();

    }

 /*   private void dummyUrlCode() {
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        JsonArrayRequest movieReq = new JsonArrayRequest(dish_images_url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        ids = new ArrayList<HomePageDish>();
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                HomePageDish movie = new HomePageDish();
                                movie.setTitle(obj.getString("dish_name"));
                                movie.setThumbnailUrl(obj.getString("dish_image_url"));

                               *//* movie.setRating(((Number) obj.get("rating"))
                                        .doubleValue());
                                movie.setYear(obj.getInt("releaseYear"));

                                // Genre is json array
                                JSONArray genreArry = obj.getJSONArray("genre");
                                ArrayList<String> genre = new ArrayList<String>();
                                for (int j = 0; j < genreArry.length(); j++) {
                                    genre.add((String) genreArry.get(j));
                                }
                                movie.setGenre(genre);*//*

                                // adding movie to movies array
                                ids.add(movie);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        mCustomPagerAdapter = new CustomPagerAdapter(MainActivity.this);
                        mPager.setAdapter(mCustomPagerAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);

    }*/

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    private void declerations() {
        mTvRestaurantList = (TextView) findViewById(R.id.btn_res_list);
        mTvBook_a_table = (TextView) findViewById(R.id.btn_book_table);
        if(appPreferences.getTABLE_NAME()==0||appPreferences.getTABLE_NAME()==9999){
           mTvBook_a_table.setText(getString(R.string.book_a_table));
        }
        else{
            mTvBook_a_table.setText(getString(R.string.move_a_table));
        }
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
        mCustomPagerAdapter = new CustomPagerAdapter(MainActivity.this);
        mPager.setAdapter(mCustomPagerAdapter);
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
                if (appPreferences.getRESTAURANT_NAME() != "") {
                    intent = new Intent(this, Service_RequestActivity.class);
                    startActivity(intent);
                } else {
                    Util.createOKAlert(this,  "", getString(R.string.restartung_selection_message));


                }
                break;
            case R.id.btn_res_list:

                intent = new Intent(this, RestaurantListAcitivity.class);
                startActivity(intent);

                break;
            case R.id.btn_res_menu:
             if (appPreferences.getRESTAURANT_NAME() != "") {
                    intent = new Intent(this, MenuActivity.class);
                    startActivity(intent);
                } else {
                 Util.createOKAlert(this,  "", getString(R.string.restartung_selection_message));
                }
                break;
            case R.id.btn_book_table:
                if (appPreferences.getRESTAURANT_NAME() != "") {
                    intent = new Intent(this, Book_a_tableActivity.class);
                    startActivity(intent);
                } else {
                    Util.createOKAlert(this,  "", getString(R.string.restartung_selection_message));
                }
                break;
            case R.id.btn_view_pay:
                if (appPreferences.getRESTAURANT_NAME() != ""&& !appPreferences.getORDER_ID().equals("")) {
                    intent = new Intent(this, View_Pay_BillActivity.class);
                    startActivity(intent);
                    /*SqlOperations sqliteoperation = new SqlOperations(getApplicationContext());
                    sqliteoperation.open();

                    if (sqliteoperation.getPayItems().size() > 0) {

                    } else {
                        Toast.makeText(this, "Please Place a order", Toast.LENGTH_SHORT).show();
                    }*/
                } else {
                    Util.createOKAlert(this,  "", getString(R.string.restartung_selection_message)+" or Place a Order");
                }
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
           /* if (imageLoader == null)
                imageLoader = AppController.getInstance().getImageLoader();*/
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
