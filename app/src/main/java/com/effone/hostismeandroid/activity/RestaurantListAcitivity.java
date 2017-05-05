package com.effone.hostismeandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.adapter.RestaurantListAdapter;
import com.effone.hostismeandroid.common.AppPreferences;
import com.effone.hostismeandroid.common.Common;
import com.effone.hostismeandroid.model.Restaurant;
import com.google.gson.Gson;

import java.util.ArrayList;

public class RestaurantListAcitivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String JSON_URL = "http://192.168.2.44/android_web_api/include/restraunt.php";
    private ListView restList;
    private RestaurantListAdapter mRestaurantListAdapter;
    AppPreferences appPreferences;
    private String mJson;
     private Gson mGson;
 private RequestQueue mQueue;
    private Restaurant[] restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list_acitivity);
        appPreferences = new AppPreferences(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Common.setCustomTitile(this,"Restaurant List","Sydney");
        mGson = new Gson();
        mQueue = Volley.newRequestQueue(this);
        init();

    }

    private void init() {

        restList = (ListView) findViewById(R.id.restaurantList);
        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mJson = response;
                        restaurants = mGson.fromJson(mJson, Restaurant[].class);
                        mRestaurantListAdapter = new RestaurantListAdapter(RestaurantListAcitivity.this, R.layout.restaurant_list_item, restaurants);
                        restList.setAdapter(mRestaurantListAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RestaurantListAcitivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
        mQueue.add(stringRequest);

        restList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent showLocationsIntent = new Intent(this, MenusActivity.class);
        String restaurant = restaurants[i].getRestName();
        appPreferences.setREST_NAME(restaurant);
        startActivity(showLocationsIntent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
