package com.effone.hostismeandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.adapter.RestaurantListAdapter;
import com.effone.hostismeandroid.common.AppPreferences;
import com.effone.hostismeandroid.model.Restaurant;

import java.util.ArrayList;

public class RestaurantListAcitivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView restList;
    private RestaurantListAdapter mRestaurantListAdapter;
    ArrayList<Restaurant> restaurants;
    AppPreferences appPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list_acitivity);
        appPreferences=new AppPreferences(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Restaurant List");
        init();

    }

    private void init() {
        restaurants = new ArrayList<Restaurant>();
        Restaurant res1 = new Restaurant("Restaurant name One", "39,Lime St", "Sydney NSW 2000", "Australia");
        Restaurant res2 = new Restaurant("Restaurant name Two", "1 maquarie St", "Sydney NSW 2000", "Australia");
        Restaurant res3 = new Restaurant("Restaurant name Three", "corner Pitt + Hunder Street", "Sydney NSW 2000", "Australia");
        Restaurant res4 = new Restaurant("Restaurant name Four", "11/ O' Connel St", "Sydney NSW 2000", "Australia");
        Restaurant res5 = new Restaurant("Restaurant name Five", "Overseas Passager Terminal Hickson Rd", "Sydney NSW 2000", "Australia");
        Restaurant res6 = new Restaurant("Restaurant name Six", "Establishment 1 establishment george Street", "Sydney NSW 2000", "Australia");
        Restaurant res7 = new Restaurant("Restaurant name Seven", "corner Pitt maquarie St", "Sydney NSW 2000", "Australia");
        restaurants.add(res1);
        restaurants.add(res2);
        restaurants.add(res3);
        restaurants.add(res4);
        restaurants.add(res5);
        restaurants.add(res6);
        restaurants.add(res7);
        restList = (ListView) findViewById(R.id.restaurantList);
        mRestaurantListAdapter = new RestaurantListAdapter(this, R.layout.restaurant_list_item, restaurants);
        restList.setAdapter(mRestaurantListAdapter);
        restList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent showLocationsIntent = new Intent( this, MenuActivity.class );
        Restaurant restaurant = restaurants.get(i);
        appPreferences.setREST_NAME(restaurant.getRestName());
        startActivity( showLocationsIntent );
    }
}
