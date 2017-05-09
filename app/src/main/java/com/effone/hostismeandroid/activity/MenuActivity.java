package com.effone.hostismeandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.adapter.HISMenuPageAdapter;
import com.effone.hostismeandroid.common.AppPreferences;
import com.effone.hostismeandroid.model.HISMenuItem;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    AppPreferences appPreferences;

    private HashMap<String, HashMap<String, List<HISMenuItem>>> mFullMenu;
    private ViewPager mVpMainMenu;
    private HashMap<String, List<HISMenuItem>> mHashMap;
    private  HISMenuPageAdapter hisMenuPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Menu");
        setPager();


        setToolbar();
        appPreferences=new AppPreferences(this);
        /*if(appPreferences.getREST_NAME()!= null)
        {

        }else {
            toolbar.setTitle(""+appPreferences.getREST_NAME());

        }*/
    }    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> menuCollection;

    private void setPager() {

        mVpMainMenu=(ViewPager)findViewById(R.id.vp_main_menu);

        // Array list for header
        ArrayList<String> header = new ArrayList<String>();

        // Array list for child items
        List<HISMenuItem> child1 = new ArrayList<HISMenuItem>();
        List<HISMenuItem> child2 = new ArrayList<HISMenuItem>();
        List<HISMenuItem> child3 = new ArrayList<HISMenuItem>();


        // Hash map for both header and child
        mHashMap = new HashMap<String, List<HISMenuItem>>();

        // Adding headers to list
        //  for (int i = 1; i < 5; i++) {
        header.add("Starters");
        header.add("Mains");
        header.add("Desserts");

        // }
        // Adding child data
        for (int i = 1; i < 3; i++) {
            child1.add(new HISMenuItem("Bread Fresh Baked Sourdough",null,0,45.00f,true));
        }
        // Adding child data
        for (int i = 1; i < 3; i++) {
            child2.add(new HISMenuItem("Roast Chiken Ballantine",null,0,90.00f,false));
        }
        // Adding child data
        for (int i = 1; i < 3; i++) {
            child3.add(new HISMenuItem("White Chocalate & Nougat Semifreddo",null,0,70.00f,false));
        }
        // Adding child data


        // Adding header and childs to hash map
        mHashMap.put(header.get(2), child3);
        mHashMap.put(header.get(0), child1);
        mHashMap.put(header.get(1), child2);


        mFullMenu=new HashMap<String, HashMap<String, List<HISMenuItem>>>();
        mFullMenu.put("Dinner",mHashMap);
        mFullMenu.put("Lunch",mHashMap);



        hisMenuPageAdapter =new HISMenuPageAdapter(getSupportFragmentManager(),mFullMenu);
        Gson gson = new Gson();
        String result = gson.toJson(mFullMenu);
        mVpMainMenu.setAdapter(hisMenuPageAdapter);
        mVpMainMenu.setOnPageChangeListener(this);
    }

    private void setToolbar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        LinearLayout customView = (LinearLayout)
                LayoutInflater.from(this).inflate(R.layout.default_custom_title,
                        null);
        ActionBar.LayoutParams params = new
                ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        getSupportActionBar().setCustomView(customView, params);
        TextView cust_ttile=(TextView)getSupportActionBar().getCustomView().findViewById(R.id.tv_custom_titile);
        cust_ttile.setText(getString(R.string.title_menu));
        TextView cust_sub_ttile=(TextView)getSupportActionBar().getCustomView().findViewById(R.id.tv_custom_sub_titile);
        cust_sub_ttile.setText("Restaurant Name One");
        ImageView iv_back=(ImageView) getSupportActionBar().getCustomView().findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        hisMenuPageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
