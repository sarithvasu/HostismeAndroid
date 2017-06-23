package com.effone.hostismeandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.effone.hostismeandroid.MainActivity;
import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.adapter.MenuItemSummeryListAdapter;
import com.effone.hostismeandroid.common.AppPreferences;
import com.effone.hostismeandroid.common.Common;
import com.effone.hostismeandroid.common.OnDataChangeListener;
import com.effone.hostismeandroid.db.SqlOperation;
import com.effone.hostismeandroid.model.CartItems;
import com.effone.hostismeandroid.model.TaxItems;
import com.effone.hostismeandroid.model_for_json.Menuitems;
import com.effone.hostismeandroid.model_for_json.Order;
import com.effone.hostismeandroid.model_for_json.OrderPlacement;
import com.effone.hostismeandroid.util.Util;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.effone.hostismeandroid.activity.MenuActivity.taxList;
import static com.effone.hostismeandroid.common.URL.POST_ORDER;
import static com.effone.hostismeandroid.db.DBConstant.ser;
import static com.effone.hostismeandroid.db.DBConstant.serviceTax;
import static com.effone.hostismeandroid.db.DBConstant.vatTax;


public class PlaceOrderActivity extends AppCompatActivity implements View.OnClickListener, OnDataChangeListener {
    private TextView mTvItemPrice, mTvItemCount, mTvChargers, mTvEstimatedTotal, mTableNo;
    private TextView mTvPlaceOrder;
    private Spinner mSpTableNo;
    private ListView mLvItemSummary;
    private AppPreferences appPrefernces;
    private Switch mCbTakeAway;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        appPrefernces = new AppPreferences(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Common.setCustomTitile(this, "View Order", null);
        decalartion();
    }

    private void decalartion() {
        mTvItemCount = (TextView) findViewById(R.id.tv_items);
        mTvItemPrice = (TextView) findViewById(R.id.tv_items_price);
        mTvChargers = (TextView) findViewById(R.id.tv_charger_price);
        mCbTakeAway = (Switch) findViewById(R.id.cb_take_away);
        mTvEstimatedTotal = (TextView) findViewById(R.id.tv_estimated_price);
        mTvPlaceOrder = (TextView) findViewById(R.id.tv_place_order);
        mTableNo = (TextView) findViewById(R.id.tableNo);
        mSpTableNo = (Spinner) findViewById(R.id.et_table_no);
        ArrayList<Integer> tableNos=new ArrayList<>();
        for (int i = 1; i <=appPrefernces.getNUMBER_OF_TABLES() ; i++) {
            tableNos.add(i);
        }
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(PlaceOrderActivity.this, android.R.layout.simple_spinner_item, tableNos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpTableNo.setAdapter(dataAdapter);
        if(appPrefernces.getTABLE_NAME()==0||appPrefernces.getTABLE_NAME()==9999) {
            mTableNo.setVisibility(View.INVISIBLE);
            mSpTableNo.setVisibility(View.INVISIBLE);
            mCbTakeAway.setChecked(true);
        }
        else {
            mCbTakeAway.setChecked(false);
            mTableNo.setVisibility(View.VISIBLE);
            mSpTableNo.setVisibility(View.VISIBLE);
        }
        if (appPrefernces.getTABLE_NAME() != 0&&appPrefernces.getTABLE_NAME()!=9999) {
            mSpTableNo.setSelection(appPrefernces.getTABLE_NAME()-1);
            mSpTableNo.setEnabled(false);
        }
        mCbTakeAway.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mTableNo.setVisibility(View.INVISIBLE);
                    mSpTableNo.setVisibility(View.INVISIBLE);
                } else {
                    if(appPrefernces.getTABLE_NAME()!=0) {
                        if(appPrefernces.getTABLE_NAME()==9999){

                            mSpTableNo.setEnabled(true);
                        }
                        mTableNo.setVisibility(View.VISIBLE);
                        mSpTableNo.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        mLvItemSummary = (ListView) findViewById(R.id.item_summery_list);
        mTvPlaceOrder.setOnClickListener(this);
        setValuesInto();
    }

    private void setValuesInto() {
        showOrderItems();
    }

    SqlOperation sqlOperation;
    ArrayList<CartItems> cartItemses;
    float totalPrice = 0;

    private void showOrderItems() {

        sqlOperation = new SqlOperation(this);
        sqlOperation.open();
        cartItemses = sqlOperation.getCartItems(1);
        float totalCount = 0;
        float totalPrice = 0;

        sqlOperation.close();
        for (int i = 0; i < cartItemses.size(); i++) {
            totalCount += cartItemses.get(i).getItemQuantity();
            totalPrice += cartItemses.get(i).getItemPrice() * cartItemses.get(i).getItemQuantity();

        }
        mTvItemPrice.setText("" + totalPrice);
        mTvItemCount.setText("Items (" + Math.round(totalCount) + ")");
        mTvChargers.setText("" + taxAmountCalculation(totalPrice));
        double sum = totalPrice + taxAmountCalculation(totalPrice);
        mTvEstimatedTotal.setText("" + sum);
        getTaxDetails((float) totalPrice);
        getOrderItemsList();
    }

    private void getOrderItemsList() {

        MenuItemSummeryListAdapter menuItemSummeryListAdapter = new MenuItemSummeryListAdapter(this, android.R.layout.simple_list_item_1, cartItemses, taxList);
        mLvItemSummary.setAdapter(menuItemSummeryListAdapter);
    }

    private float taxAmountCalculation(float totalPrice) {
        float chargers = 0;
        for(int i=0;i<taxList.size();i++){
            if(taxList.get(i).getType().equals("Fixed")){
             chargers   = chargers+ totalPrice +Float.parseFloat(taxList.get(i).getChargevalue());
            }else{
                chargers = chargers + (totalPrice / 100.0f) * Float.parseFloat(taxList.get(i).getChargevalue());
            }
        }

        return chargers;
    }

    ArrayList<TaxItems> taxItemses;

    private void getTaxDetails(float totalByItems) {
        taxItemses = new ArrayList<TaxItems>();
        TaxItems res1 = new TaxItems("Total before Tax", totalByItems);
        taxItemses.add(res1);
        for(int i=0;i<taxList.size();i++){
            float chargers;
            if(taxList.get(i).getType().equals("Fixed")){
                chargers   = + totalPrice +Float.parseFloat(taxList.get(i).getChargevalue());
            }else{
                chargers = + (totalPrice / 100.0f) * Float.parseFloat(taxList.get(i).getChargevalue());
            }
            TaxItems taxi= new TaxItems(taxList.get(i).getName(),chargers);
            taxItemses.add(taxi);
        }
        totalPrice = totalByItems;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_place_order) {
            String mTableName = "";
            if (mCbTakeAway.isChecked())
                mTableName = "9999";
            else
                mTableName = mSpTableNo.getSelectedItem().toString().trim();
            if (mTableName.length() >= 1) {

                ArrayList<Order> orderToServers = new ArrayList<>();
                Order orderToServer = new Order();
                orderToServer.setRestaurantId(appPrefernces.getRESTAURANT_ID());
                orderToServer.setDeviceId(appPrefernces.getDEVICE_ID());


                if (appPrefernces.getORDER_ID() != null)
                    orderToServer.setId(appPrefernces.getORDER_ID());
                else
                    orderToServer.setId("");
                appPrefernces.setTABLE_NAME(Integer.parseInt(mTableName));
                orderToServer.setTableno(Integer.parseInt(mTableName));
                orderToServer.setOrderprice(totalPrice);
                double sum = totalPrice + taxAmountCalculation(totalPrice);
                orderToServer.setTotalprice(sum);
                orderToServer.setTax(taxAmountCalculation(totalPrice));
                    int count=0;
                String descriptoin = null;
                ArrayList<Menuitems> orderingMenus = new ArrayList<>();
                for (CartItems cartItems : cartItemses) {
                    if(cartItems.getSpecial() != null) {
                        if (cartItems.getSpecial().equals("0")) {
                            cartItems.setSpecial("1");//not speical item will be 1
                        } else if (cartItems.getSpecial().equals("1")) {
                            cartItems.setSpecial("2");//  speical item will be 2
                        } else{
                            cartItems.setSpecial("3");
                        }
                    }else {
                       cartItems.setSpecial("3");//bevagrages will be 3
                   }
                   count = count+cartItems.getItemQuantity();
                    descriptoin=cartItems.getItemCatagerie();
                    orderingMenus.add(new Menuitems(cartItems.getItemMenuCatId(), cartItems.getItemQuantity(),cartItems.getSpecial(),cartItems.getMenuType()));
                }
                appPrefernces.setQunatity(count);
                if(!descriptoin.equals("")){
                if(descriptoin.equals("Breakfast")){
                    orderToServer.setPhaseid(""+1);
                }else if(descriptoin.equals("Lunch")) {
                    orderToServer.setPhaseid(""+2);
                }else if(descriptoin.equals("Dinner")){
                    orderToServer.setPhaseid(""+3);
                }else if(descriptoin.equals("Allday")){
                    orderToServer.setPhaseid(""+4);
                }
                }

                appPrefernces.setDESCRIPTION(descriptoin);
                orderToServer.setMenuitems(orderingMenus);
                OrderPlacement orderPlacement=new OrderPlacement();
                orderPlacement.setOrder(orderToServer);
                Gson gson = new Gson();
                String json = gson.toJson(orderPlacement);
                if(count != 0) {
                    pushDataToServer(json);
                }else{
                    Util.createOKAlert(PlaceOrderActivity.this,"Heading","No Items Found...");
                }
            } else {
                Util.createOKAlert(PlaceOrderActivity.this,"Heading","Please enter the Table no");
            }
        }
    }
    private void pushDataToServer(final String mTableName) {
        StringRequest req = new StringRequest(Request.Method.POST, POST_ORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String value = response;
                        if (!value.equals("")) {
                           // Toast.makeText(PlaceOrderActivity.this, " " + response, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(PlaceOrderActivity.this, ConfirmationActivity.class);
                            intent.putExtra("Order_id", response);
                            startActivity(intent);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.createOKAlert(PlaceOrderActivity.this,"",error.getMessage()+"Ërror has Encounterd");
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return mTableName.getBytes();
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(req);
    }
    @Override
    public void onDataChanged(int size) {
        setValuesInto();
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
}
