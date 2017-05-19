package com.effone.hostismeandroid.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.adapter.OrderItemDetailsAdapter;
import com.effone.hostismeandroid.adapter.TaxDetailsAdapter;
import com.effone.hostismeandroid.common.AppPreferences;
import com.effone.hostismeandroid.common.Common;
import com.effone.hostismeandroid.db.SqlOperation;
import com.effone.hostismeandroid.model.OrderSummary;
import com.effone.hostismeandroid.model.Order_Items;
import com.effone.hostismeandroid.model.TaxItems;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.effone.hostismeandroid.common.URL.get_placed_order;
import static com.effone.hostismeandroid.db.DBConstant.ser;
import static com.effone.hostismeandroid.db.DBConstant.serviceTax;
import static com.effone.hostismeandroid.db.DBConstant.vatTax;

/**
 * Created by sumanth.peddinti on 5/15/2017.
 */

public class ConfirmationActivity extends AppCompatActivity {

    private TextView mTvDateTime, mTvRestName, mTvBookingId, mTvDescription, mTvTableNo, mTvQuantits, mTvOrderTotal, mTvStatus, mTvTotalPrice;
    private TextView mTvConfirmationMessage;
    private ListView mLvItemQuantity, mLvTaxQuality, mListView;
    private RelativeLayout mRelativeLayout;
    private SqlOperation sqliteoperation;
    private String order_id, mStatus;
    private Gson mGson;
    private RequestQueue mQueue;
    private String urls;
    private OrderSummary[] mOrderToServers;
    private AppPreferences mAppPrefernces;
    SqlOperation sqlOperation;
    private OrderItemDetailsAdapter orderItemDetails;
    private ArrayList<TaxItems> taxItemses;
    private TaxDetailsAdapter taxDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confiramtion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Common.setCustomTitile(this,"Order Confirmation",null);
        mAppPrefernces = new AppPreferences(this);
        mGson = new Gson();
        mQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        String jsondata = intent.getStringExtra("Order_id");
        JSONObject myJson = null;
        try {
            myJson = new JSONObject(jsondata);
            mStatus = myJson.getString("msg");
            order_id = myJson.getString("order_id");
            urls = get_placed_order + order_id;
            mAppPrefernces.setORDER_ID(order_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
// use myJson as needed, for example
        sqliteoperation = new SqlOperation(getApplicationContext());
        sqliteoperation.open();
        //   List<HashMap<String, String>> dictionary = sqliteoperation.getPlaceOrder(order_id);
        sqliteoperation.close();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            init();
        }
        // sqliteoperation.updatetheCart();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void init() {
        mTvConfirmationMessage = (TextView) findViewById(R.id.tv_confiramtion_msg);
        mTvConfirmationMessage.setText(mStatus);
        mLvItemQuantity = (ListView) findViewById(R.id.lv_items_list);
        mLvTaxQuality = (ListView) findViewById(R.id.lv_tax_menu);
        mListView = (ListView) findViewById(R.id.historyView);
        mListView.setVisibility(View.GONE);
        AppBarLayout titel_na=(AppBarLayout) findViewById(R.id.titel_na);
        titel_na.setVisibility(View.GONE);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mRelativeLayout.setBackground(getDrawable(R.drawable.payment_background));
        }
        mTvDateTime = (TextView) findViewById(R.id.tv_data_time);
        mTvRestName = (TextView) findViewById(R.id.tv_rest_name);
        mTvBookingId = (TextView) findViewById(R.id.tv_booking_id);
        mTvDescription = (TextView) findViewById(R.id.tv_descrption);
        mTvTableNo = (TextView) findViewById(R.id.tv_table_no);
        mTvQuantits = (TextView) findViewById(R.id.tv_quantitys);
        mTvOrderTotal = (TextView) findViewById(R.id.tv_order_total);
        mTvStatus = (TextView) findViewById(R.id.tv_order_status);
        mTvTotalPrice = (TextView) findViewById(R.id.tv_total_price);
        settingValues();
    }

    private void settingValues() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urls,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mOrderToServers = mGson.fromJson(response, OrderSummary[].class);
                        showingData();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ConfirmationActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        mQueue.add(stringRequest);

    }

    private void showingData() {
        mTvDateTime.setText(": " + mOrderToServers[0].getDatatime());
        mTvRestName.setText(": " + mOrderToServers[0].getRestaurant_id());
        mAppPrefernces.setORDER_ID(mOrderToServers[0].getOrder_id());
        mTvBookingId.setText(": " + mOrderToServers[0].getOrder_id());
        mTvOrderTotal.setText(": $ " + mOrderToServers[0].getTotal_price());
        mTvStatus.setText(": " + mOrderToServers[0].getStatus());
        showingDataIntoListView();
    }

    double totalPrice=0;
    private void showingDataIntoListView() {

        sqliteoperation.open();
        String[] values = new String[mOrderToServers[0].getItems_values().length];
        for (int i = 0; i < values.length; i++) {
            values[i] = mOrderToServers[0].getItems_values()[i].getItem_id();
        }
        sqliteoperation.setOrderFlagsUpdate(Integer.parseInt(order_id));
        ArrayList<Order_Items> order_itemses = sqliteoperation.getItemName(values, order_id);

        sqliteoperation.close();
        mTvDescription.setText(": " + mOrderToServers[0].getDatatime());
        mTvTableNo.setText(": " + mOrderToServers[0].getTable_no());

        int quantity = 0;

        for (int i = 0; i < order_itemses.size(); i++) {
            quantity += order_itemses.get(i).getQuantity();
            totalPrice +=order_itemses.get(i).getPrice()* order_itemses.get(i).getQuantity();
        }

        mTvQuantits.setText(": " + quantity);
        orderItemDetails = new OrderItemDetailsAdapter(this, R.layout.order_summary_items, order_itemses);
        mLvItemQuantity.setAdapter(orderItemDetails);
        populatingTaxMenuList();

    }



    private void populatingTaxMenuList() {

        taxItemses = new ArrayList<TaxItems>();
        TaxItems res1 = new TaxItems("Total before Tax", totalPrice);
        TaxItems res2 = new TaxItems("Service Charges", serviceTax);
        TaxItems res3 = new TaxItems("Service Tax", ser);
        TaxItems res4 = new TaxItems("VAT Tax", vatTax);
        taxItemses.add(res1);
        taxItemses.add(res2);
        taxItemses.add(res3);
        taxItemses.add(res4);
        taxDetailsAdapter = new TaxDetailsAdapter(this, R.layout.tax_items, taxItemses);
        mLvTaxQuality.setAdapter(taxDetailsAdapter);
        double sum = Double.parseDouble(mOrderToServers[0].getTotal_price()) + serviceTax + ser + vatTax;
        mTvTotalPrice.setText("$ " + sum);
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
