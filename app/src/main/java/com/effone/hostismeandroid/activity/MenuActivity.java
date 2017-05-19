package com.effone.hostismeandroid.activity;

import android.app.Application;
import android.content.Intent;
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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.effone.hostismeandroid.MainActivity;
import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.adapter.HISMenuPageAdapter;
import com.effone.hostismeandroid.common.AppPreferences;
import com.effone.hostismeandroid.common.Common;
import com.effone.hostismeandroid.common.OnDataChangeListener;
import com.effone.hostismeandroid.common.OnHandClickInterface;
import com.effone.hostismeandroid.common.UpdateableInterface;
import com.effone.hostismeandroid.db.SqlOperation;
import com.effone.hostismeandroid.model.CartItems;
import com.effone.hostismeandroid.model.HISMenuItem;
import com.effone.hostismeandroid.model.Items;
import com.effone.hostismeandroid.model.Sample;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.effone.hostismeandroid.common.URL.menu_url;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener,UpdateableInterface,OnDataChangeListener,OnHandClickInterface {

    private String mJson;
    private Gson mGson;
    private RequestQueue mQueue;
    private Sample sample;
    private  ViewPager mVpMainMenu;
    private AppPreferences mAppPreferences;

    private TextView mTvConfirm,mTvSummary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_menu);
        mAppPreferences=new AppPreferences(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setToolbar();
        mVpMainMenu=(ViewPager)findViewById(R.id.vp_main_menu);
        mVpMainMenu.setCurrentItem(1,true);
        mTvConfirm=(TextView)findViewById(R.id.tv_confirm);
        mTvSummary=(TextView)findViewById(R.id.tv_summary_details);
        mTvConfirm.setOnClickListener(this);
        mGson = new Gson();
        mQueue = Volley.newRequestQueue(this);
        showOrderItems();
        StringRequest stringRequest = new StringRequest(menu_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mJson = response;
                        sample = mGson.fromJson(mJson, Sample.class);

                        HashMap<String ,Items[]> pagerItem=new LinkedHashMap<>();
                        for (int i = 0; i <sample.getMenu().getCategories().length ; i++) {

                            pagerItem.put(sample.getMenu().getCategories()[i].getName(),sample.getMenu().getCategories()[i].getItems());
                        }
                        HISMenuPageAdapter menuPageAdapter= new HISMenuPageAdapter(getSupportFragmentManager(),pagerItem);
                        mVpMainMenu.setAdapter(menuPageAdapter);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MenuActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
        mQueue.add(stringRequest);

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
        cust_sub_ttile.setText(mAppPreferences.getRESTAURANT_NAME());
        ImageView iv_back=(ImageView) getSupportActionBar().getCustomView().findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_confirm:
                Intent intent=new Intent(this,PlaceOrderActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    SqlOperation sqlOperation;
    ArrayList<CartItems>  cartItemses;
    private void showOrderItems() {
        sqlOperation=new SqlOperation(this);
        sqlOperation.open();
        cartItemses = sqlOperation.getCartItems(1);
        int totalCount=0;
        int totalPrice=0;
        sqlOperation.close();
        for (int i = 0; i <cartItemses.size(); i++) {
            totalCount +=  cartItemses.get(i).getItemQuantity();
            totalPrice += cartItemses.get(i).getItemPrice()*cartItemses.get(i).getItemQuantity();

        }
        mTvSummary.setText(totalCount+" Items in Cart \n "+ totalPrice+" Plus charges");
    }

    @Override
    public void update() {
        showOrderItems();
    }

    @Override
    protected void onStart() {
        super.onStart();
        showOrderItems();
    }

    @Override
    public void onDataChanged(int size) {
        showOrderItems();
    }

    @Override
    public void getFragmentPosition(int postion) {
        mVpMainMenu.setCurrentItem(mVpMainMenu.getCurrentItem()+1, true);

    }
}