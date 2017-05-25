package com.effone.hostismeandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
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
import com.effone.hostismeandroid.model.OrderToServer;
import com.effone.hostismeandroid.model.OrderingMenu;
import com.effone.hostismeandroid.model.TaxItems;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.effone.hostismeandroid.common.URL.place_order_url;
import static com.effone.hostismeandroid.db.DBConstant.ser;
import static com.effone.hostismeandroid.db.DBConstant.serviceTax;
import static com.effone.hostismeandroid.db.DBConstant.vatTax;


public class PlaceOrderActivity extends AppCompatActivity implements View.OnClickListener, OnDataChangeListener {
    private TextView mTvItemPrice, mTvItemCount, mTvChargers, mTvEstimatedTotal,mTableNo;
    private TextView mTvPlaceOrder;
    private EditText mEtTableNo;
    private ListView mLvItemSummary;
    private AppPreferences appPrefernces;
    private Switch mCbTakeAway;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        appPrefernces=new AppPreferences(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // getSupportActionBar().setTitle(getString(R.string.booking_history));
        Common.setCustomTitile(this,"View Order",null);
        decalartion();
    }

    private void decalartion() {
        mTvItemCount = (TextView) findViewById(R.id.tv_items);
        mTvItemPrice = (TextView) findViewById(R.id.tv_items_price);
        mTvChargers = (TextView) findViewById(R.id.tv_charger_price);
        mCbTakeAway=(Switch)findViewById(R.id.cb_take_away);
        mCbTakeAway.setChecked(true);
        mTvEstimatedTotal = (TextView) findViewById(R.id.tv_estimated_price);
        mTvPlaceOrder = (TextView) findViewById(R.id.tv_place_order);
        mTableNo= (TextView) findViewById(R.id.tableNo);
        mEtTableNo = (EditText) findViewById(R.id.et_table_no);
        mTableNo.setVisibility(View.INVISIBLE);
        mEtTableNo.setVisibility(View.INVISIBLE);
        if(appPrefernces.getTABLE_NAME() !=  0){
            mEtTableNo.setText(""+appPrefernces.getTABLE_NAME());
            mEtTableNo.setFocusable(false);
        }
        mCbTakeAway.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    mTableNo.setVisibility(View.INVISIBLE);
                    mEtTableNo.setVisibility(View.INVISIBLE);
                }
                else{
                    mTableNo.setVisibility(View.VISIBLE);
                    mEtTableNo.setVisibility(View.VISIBLE);
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
        mTvChargers.setText("" + taxAmountCalculation());
        double sum = totalPrice + taxAmountCalculation();
        mTvEstimatedTotal.setText("" + sum);
        getTaxDetails(totalPrice);
        getOrderItemsList();

    }

    private void getOrderItemsList() {
        MenuItemSummeryListAdapter menuItemSummeryListAdapter = new MenuItemSummeryListAdapter(this, android.R.layout.simple_list_item_1, cartItemses, taxItemses);
        mLvItemSummary.setAdapter(menuItemSummeryListAdapter);

    }

    private float taxAmountCalculation() {
        return (float) (serviceTax + vatTax + ser);
    }


    ArrayList<TaxItems> taxItemses;

    private void getTaxDetails(float totalByItems) {

        taxItemses = new ArrayList<TaxItems>();
        TaxItems res1 = new TaxItems("Total before Tax", totalByItems);
        TaxItems res2 = new TaxItems("Service Charges", serviceTax);
        TaxItems res3 = new TaxItems("Service Tax", vatTax);
        TaxItems res4 = new TaxItems("VAT Tax", ser);
        taxItemses.add(res1);
        taxItemses.add(res2);
        taxItemses.add(res3);
        taxItemses.add(res4);
        totalPrice=totalByItems;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tv_place_order) {
            String mTableName="";
            if(mCbTakeAway.isChecked())
                mTableName="9999";
            else
             mTableName = mEtTableNo.getText().toString().trim();
            if (mTableName.length() > 2) {
                sqlOperation = new SqlOperation(this);
                sqlOperation.open();
                sqlOperation.setFlagaUpdate();
                sqlOperation.close();
                ArrayList<OrderToServer> orderToServers= new ArrayList<>();
                OrderToServer orderToServer= new OrderToServer();
                orderToServer.setLocationId(22);
                orderToServer.setRestaurant_id(555);
                if(appPrefernces.getORDER_ID() != null)
                orderToServer.setOrder_id(appPrefernces.getORDER_ID());
                else
                    orderToServer.setOrder_id("");
                orderToServer.setTable_no(Integer.parseInt(mTableName));
                orderToServer.setTotal_price(totalPrice);
                ArrayList<OrderingMenu> orderingMenus=new ArrayList<>();
                for (CartItems cartItems:cartItemses) {
                    orderingMenus.add(new OrderingMenu(cartItems.getItemMenuCatId(),cartItems.ItemQuantity));
                }
                orderToServer.setItems(orderingMenus);
                orderToServers.add(orderToServer);
                Gson gson= new Gson();
                String json=gson.toJson(orderToServers);
                pushDataToServer(json);

            } else {
                Toast.makeText(this, "Please enter the Table no", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void pushDataToServer(final String mTableName) {
        StringRequest req = new StringRequest(Request.Method.POST, place_order_url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String value = response;
                        if (!value.equals("")) {
                            Toast.makeText(PlaceOrderActivity.this," "+response,Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(PlaceOrderActivity.this,ConfirmationActivity.class);
                            intent.putExtra("Order_id",response);
                            startActivity(intent);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PlaceOrderActivity.this," "+error,Toast.LENGTH_LONG).show();
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
