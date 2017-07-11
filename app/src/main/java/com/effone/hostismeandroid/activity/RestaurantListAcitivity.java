package com.effone.hostismeandroid.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.effone.hostismeandroid.MainActivity;
import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.adapter.RestaurantListAdapter;
import com.effone.hostismeandroid.common.AppPreferences;
import com.effone.hostismeandroid.common.Common;
import com.effone.hostismeandroid.db.SqlOperation;
import com.effone.hostismeandroid.model.Restaurant;
import com.effone.hostismeandroid.util.Util;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.effone.hostismeandroid.common.URL.GET_RESTAURANT_LIST;

public class RestaurantListAcitivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView restList;
    private RestaurantListAdapter mRestaurantListAdapter;
    AppPreferences appPreferences;
    private String mJson;
    private Gson mGson;
    private RequestQueue mQueue;
    private Restaurant[] restaurants;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list_acitivity);
        appPreferences = new AppPreferences(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Common.setCustomTitile(this, getString(R.string.Restaurant_List), getString(R.string.Sydney));
        mGson = new Gson();
        mQueue = Volley.newRequestQueue(this);
        if (!Util.Operations.isOnline(this)) {
            Intent intent = new Intent(getApplicationContext(), NoNetworkActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else
            init();

    }

    private void init() {
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage(getString(R.string.loading));
        pDialog.show();
        restList = (ListView) findViewById(R.id.restaurantList);
        StringRequest stringRequest = new StringRequest(GET_RESTAURANT_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObjec = new JSONObject(response);
                            boolean status = jsonObjec.getBoolean("status");
                            if (status) {

                                restaurants = mGson.fromJson(jsonObjec.getString("Restaurantlist"), Restaurant[].class);
                                mRestaurantListAdapter = new RestaurantListAdapter(RestaurantListAcitivity.this, R.layout.restaurant_list_item, restaurants);
                                restList.setAdapter(mRestaurantListAdapter);
                                hidePDialog();
                            }
                            hidePDialog();
                        } catch (JSONException e) {
                            hidePDialog();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hidePDialog();
                        Util.createErrorAlert(RestaurantListAcitivity.this, "", getString(R.string.server_error));
                    }
                });
        mQueue.add(stringRequest);

        restList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (appPreferences.getORDER_ID().equals("")) {

            final String restaurant = restaurants[i].getRestaurant_name();
            final String address = restaurants[i].getArea() + ", " + restaurants[i].getStreet() + ", " + restaurants[i].getState() + " " + restaurants[i].getPincode();
            final int table_nos=Integer.parseInt(restaurants[i].getNo_of_tables());
            final String restaurant_id = restaurants[i].getId();
            if (!appPreferences.getRESTAURANT_ID().equals(restaurant_id) && appPreferences.getTABLE_NAME() != 0) {

                final SqlOperation sqlOperation = new SqlOperation(this);
                sqlOperation.open();
                if (sqlOperation.getCount() > 0) {
                    Util.createOkCancelDialog(RestaurantListAcitivity.this,  getString(R.string.warning_restarant_selection), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sqlOperation.delete();
                            sqlOperation.close();
                            appPreferences.setTABLE_NAME(0);
                            appPreferences.setORDER_ID("");

                            appPreferences.setRRESTAURANT_NAME(restaurant);
                            appPreferences.setRESTAURANT_ID(restaurant_id);
                            appPreferences.setRESTAURANT_ADDRESS(address);
                            appPreferences.setNUMBER_OF_TABLES(table_nos);

                            Intent showLocationsIntent = new Intent(RestaurantListAcitivity.this, Book_a_tableActivity.class);
                            startActivity(showLocationsIntent);

                        }
                    });
                } else {
                    sqlOperation.close();
                    appPreferences.setTABLE_NAME(0);
                    appPreferences.setORDER_ID("");

                    appPreferences.setRRESTAURANT_NAME(restaurant);
                    appPreferences.setRESTAURANT_ID(restaurant_id);
                    appPreferences.setRESTAURANT_ADDRESS(address);
                    appPreferences.setNUMBER_OF_TABLES(Integer.parseInt(restaurants[i].getNo_of_tables()));

                    Intent showLocationsIntent = new Intent(RestaurantListAcitivity.this, Book_a_tableActivity.class);
                    startActivity(showLocationsIntent);
                }

            }
            else{
                appPreferences.setRRESTAURANT_NAME(restaurant);
                appPreferences.setRESTAURANT_ID(restaurant_id);
                appPreferences.setRESTAURANT_ADDRESS(address);
                appPreferences.setNUMBER_OF_TABLES(Integer.parseInt(restaurants[i].getNo_of_tables()));

                Intent showLocationsIntent = new Intent(RestaurantListAcitivity.this, Book_a_tableActivity.class);
                startActivity(showLocationsIntent);
            }

        } else {
            Util.createOKAlert(this, "", "" + getString(R.string.payment_msg));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                final View v = findViewById(R.id.home_btn);

                if (v != null) {
                    v.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            return false;
                        }
                    });
                }
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        if (item.getItemId() == R.id.home_btn) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
