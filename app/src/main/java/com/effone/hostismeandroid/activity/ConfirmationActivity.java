package com.effone.hostismeandroid.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.effone.hostismeandroid.MainActivity;
import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.adapter.OrderItemConfirmationAdapter;
import com.effone.hostismeandroid.adapter.TaxitemConfirmationAdapter;
import com.effone.hostismeandroid.common.AppPreferences;
import com.effone.hostismeandroid.common.Common;
import com.effone.hostismeandroid.db.SqlOperation;
import com.effone.hostismeandroid.model.OrderSummary;
import com.effone.hostismeandroid.model.TaxItems;
import com.effone.hostismeandroid.model_for_confirmation.RootObject;
import com.effone.hostismeandroid.model_for_json.OrderItem;
import com.effone.hostismeandroid.model_for_json.OrderSumm;
import com.effone.hostismeandroid.util.Util;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
    private OrderItemConfirmationAdapter orderItemDetails;
    private ArrayList<TaxItems> taxItemses;
    private TaxitemConfirmationAdapter taxDetailsAdapter;
    private RootObject mBill;
    private String jsondata;
    private OrderItem[] orderItems;
    private OrderSumm orderSumm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confiramtion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sqlOperation = new SqlOperation(this);
        sqlOperation.open();
        sqlOperation.delete();
        sqlOperation.close();
      /*  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
        Common.setCustomTitileCOnfirmation(this, "Order Confirmation", null);
        mAppPrefernces = new AppPreferences(this);
        mGson = new Gson();
        mQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        jsondata = intent.getStringExtra("Order_id");


        try {
            JSONObject jsonObjec = new JSONObject(jsondata);
            JSONObject menuJsonObject = jsonObjec.getJSONObject("orderConfirmation");
            String oderSummary = menuJsonObject.getString("order_summary");
            JSONObject order_details = menuJsonObject.getJSONObject("order_details");
            String orderIterms = order_details.getString("orderItems");
            String taxItems = order_details.getString("taxItems");
            mLvTaxQuality = (ListView) findViewById(R.id.lv_tax_menu);
            getingtax(order_details);
            orderItems = mGson.fromJson(String.valueOf(orderIterms), OrderItem[].class);
            orderSumm = mGson.fromJson(oderSummary, OrderSumm.class);
     /*       JSONArray taxItemss = order_details.getJSONArray("Taxes");
            String data=oderSummary.getString("orderts");
            String order=oderSummary.getString("order_id");
            String totalprice=oderSummary.getString("totalprice");
            String orderstatus=oderSummary.getString("orderstatus");*/
        } catch (Exception e) {

        }

        sqliteoperation = new SqlOperation(getApplicationContext());
        sqliteoperation.open();
        //   List<HashMap<String, String>> dictionary = sqliteoperation.getPlaceOrder(order_id);
        sqliteoperation.close();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            init();
        }
        // sqliteoperation.updatetheCart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!Util.Operations.isOnline(this)) {
            Util.createNetErrorDialog(this);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void init() {
        mTvConfirmationMessage = (TextView) findViewById(R.id.tv_confiramtion_msg);
        mLvItemQuantity = (ListView) findViewById(R.id.lv_items_list);
        mListView = (ListView) findViewById(R.id.historyView);
        mListView.setVisibility(View.GONE);
        AppBarLayout titel_na = (AppBarLayout) findViewById(R.id.titel_na);
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
        showingData();
        hidePDialog();
    }

    private ProgressDialog pDialog;

    private void settingValues() {
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        showingDataIntoListView();

    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    private void showingData() {
        mTvDateTime.setText(": " + orderSumm.getOrderts());
        mTvRestName.setText(": " + mAppPrefernces.getRESTAURANT_NAME());
        mAppPrefernces.setORDER_ID("" + orderSumm.getOrder_id());

        mTvDescription.setText(": " + mAppPrefernces.getDescription());
        if (mAppPrefernces.getTABLE_NAME() == 9999)
               mTvTableNo.setText(": " +getString(R.string.take_away));
        else if (mAppPrefernces.getTABLE_NAME() == 8888)
            mTvTableNo.setText(": " + getString(R.string.bar));
        else
            mTvTableNo.setText(": " + mAppPrefernces.getTABLE_NAME());

        mTvQuantits.setText(": " + mAppPrefernces.getQunatity());
        mTvBookingId.setText(": " + orderSumm.getOrder_id());
        mTvOrderTotal.setText(": $ " + orderSumm.getTotalprice());
        mTvStatus.setText(": " + orderSumm.getOrderstatus());
        mTvTotalPrice.setText("$ " + orderSumm.getTotalprice());
        hidePDialog();
        showingDataIntoListView();
    }

    double totalPrice = 0;

    private void showingDataIntoListView() {
        List<OrderItem> list = Arrays.asList(orderItems);
        orderItemDetails = new OrderItemConfirmationAdapter(ConfirmationActivity.this, R.layout.order_summary_items, list);
        mLvItemQuantity.setAdapter(orderItemDetails);
        setListViewHeightBasedOnItems(mLvItemQuantity);

        //populatingTaxMenuList();

    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                float px = 500 * (listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int) px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);
            // Get padding
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();
            return true;

        } else {
            return false;
        }

    }

    private void getingtax(JSONObject os) {
        taxItemses = new ArrayList<>();
        try {
            JSONArray json = os.getJSONArray("taxItems");
            JSONObject object = json.getJSONObject(0);
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            try {
                Iterator<String> iterator = object.keys();

                while (iterator.hasNext()) {
                    String key = iterator.next();
                    double value = Double.parseDouble(object.getString(key));
                    TaxItems taxitem = new TaxItems();
                    taxitem.setName(key);
                    taxitem.setValue(value);

                    taxItemses.add(taxitem);
                }
                taxDetailsAdapter = new TaxitemConfirmationAdapter(this, R.layout.tax_items, taxItemses);
                mLvTaxQuality.setAdapter(taxDetailsAdapter);
               /* double sum = mBill.getOrderConfirmation().getOrderSummary().getTotalprice()+ serviceTax + ser + vatTax;
                mTvTotalPrice.setText("$ " + sum);*/
                setListViewHeightBasedOnItems(mLvTaxQuality);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void populatingTaxMenuList() {

        taxItemses = new ArrayList<TaxItems>();
        TaxItems res1 = new TaxItems("Total before Tax", totalPrice);


        taxItemses.add(res1);
        taxDetailsAdapter = new TaxitemConfirmationAdapter(this, R.layout.tax_items, taxItemses);
        mLvTaxQuality.setAdapter(taxDetailsAdapter);
        double sum = Double.parseDouble(mOrderToServers[0].getTotal_price()) + serviceTax + ser + vatTax;
        mTvTotalPrice.setText("$ " + sum);
    }

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
    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
