package com.effone.hostismeandroid.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.effone.hostismeandroid.MainActivity;
import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.adapter.Booking_HistoryAdapter;
import com.effone.hostismeandroid.common.AppPreferences;
import com.effone.hostismeandroid.common.Common;
import com.effone.hostismeandroid.db.SqlOperations;
import com.effone.hostismeandroid.model.BookingHistoryItem;
import com.effone.hostismeandroid.model.OrderSummary;
import com.effone.hostismeandroid.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.effone.hostismeandroid.common.URL.GET_BOOKING_HISTORY;

/**
 * Created by sarith.vasu on 14-04-2017.
 */

public class My_BookingActivity extends AppCompatActivity {
    private AppPreferences appPreferences;
    private ListView mLvBookingHistory;
    private Booking_HistoryAdapter bookingHistoryAdapter;
    private ArrayList<OrderSummary> orderSummaries;
    private RelativeLayout mRelativeLayout;
    private SqlOperations sqliteoperation;
    Long bill_no = Long.valueOf(000000);
    ArrayList<BookingHistoryItem> mBookingHistoryItem;
    private ProgressDialog pDialog;

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_history_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // sqliteoperation = new SqlOperations(getApplicationContext());
        //sqliteoperation.open();
        gson = new Gson();
        //mPaymentConfirmation = (ArrayList<BookingHistoryItem>) sqliteoperation.getBookedHistory(bill_no);
        // sqliteoperation.close();
        appPreferences = new AppPreferences(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Common.setCustomTitile(this, getString(R.string.booking_history), null);
      /*  if(appPreferences.getREST_NAME()!= null)
        {

        }else {
            toolbar.setTitle(""+appPreferences.getREST_NAME());

        }*/
        init();
    }

    private void init() {
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        mRelativeLayout.setVisibility(View.GONE);
        mLvBookingHistory = (ListView) findViewById(R.id.historyView);
        //virtualMethod();
       /* Gson gson= new Gson();
        String json = gson.toJson(mPaymentConfirmation);*/
        if (!Util.Operations.isOnline(this))
            Util.createNetErrorDialog(this);
        else
            dummyUrlCode();

    }

    private void dummyUrlCode() {
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage(getString(R.string.loading));
        pDialog.show();
        StringRequest movieReq = new StringRequest(GET_BOOKING_HISTORY + appPreferences.getDEVICE_ID(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonObjec = null;
                        try {
                            jsonObjec = new JSONObject(response);
                            boolean status = jsonObjec.getBoolean("status");
                            if (status) {
                                Gson gson = new Gson();

                                mBookingHistoryItem = gson.fromJson(jsonObjec.getString("bookinghistory"), new TypeToken<ArrayList<BookingHistoryItem>>() {
                                }.getType());
                                bookingHistoryAdapter = new Booking_HistoryAdapter(My_BookingActivity.this, R.layout.booking_history_items, mBookingHistoryItem);
                                mLvBookingHistory.setAdapter(bookingHistoryAdapter);
                                hidePDialog();
                            } else {
                                Util.createErrorAlert(My_BookingActivity.this, "", ""+getString(R.string.no_order_items));
                           /* Intent intent=new Intent(My_BookingActivity.this,MainActivity.class);
                            startActivity(intent);*/
                            }
                            hidePDialog();
                        } catch (JSONException e) {
                            hidePDialog();
                            e.printStackTrace();
                        }


                        // Parsing json

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidePDialog();
                Intent intent = new Intent(My_BookingActivity.this, MainActivity.class);
                startActivity(intent);
//                Util.createErrorAlert(My_BookingActivity.this, "", error.getMessage());


            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(movieReq);
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
/*
    private void virtualMethod() {
        orderSummaries=new ArrayList<OrderSummary>();
        OrderSummary order_items=new OrderSummary("11 Apr 2017-14:30PM","Restaurant Name One","Sy56002019924","",45, 0 ,246.00,"Booked");
        OrderSummary order_items1=new OrderSummary("12 Apr 2017-14:30PM","Restaurant Name One","Sy56002019924","",45, 0 ,246.00,"Booked");
        OrderSummary order_items2=new OrderSummary("13 Apr 2017-14:30PM","Restaurant Name One","Sy56002019924","",45, 0 ,246.00,"Booked");

        orderSummaries.add(order_items);
        orderSummaries.add(order_items1);
        orderSummaries.add(order_items2);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
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


}
