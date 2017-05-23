package com.effone.hostismeandroid.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.effone.hostismeandroid.MainActivity;
import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.adapter.OrderItemDetailsAdapter;
import com.effone.hostismeandroid.adapter.RestaurantListAdapter;
import com.effone.hostismeandroid.adapter.TaxDetailsAdapter;
import com.effone.hostismeandroid.app.AppController;
import com.effone.hostismeandroid.common.AppPreferences;
import com.effone.hostismeandroid.common.Common;
import com.effone.hostismeandroid.db.SqlOperations;
import com.effone.hostismeandroid.model.Bill;
import com.effone.hostismeandroid.model.Order_Items;
import com.effone.hostismeandroid.model.OrderedItemSummary;
import com.effone.hostismeandroid.model.Restaurant;
import com.effone.hostismeandroid.model.TaxItems;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static com.effone.hostismeandroid.activity.ViewCartActivity.ser;
import static com.effone.hostismeandroid.activity.ViewCartActivity.serviceTax;
import static com.effone.hostismeandroid.activity.ViewCartActivity.vatTax;
import static com.effone.hostismeandroid.common.URL.bill_url;

/**
 * Created by sumanth.peddinti on 4/13/2017.
 */

public class View_Pay_BillActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvSelectedDate;
    private ListView mLvItemQuantity,mLvTaxQuality;
    private TaxDetailsAdapter taxDetailsAdapter;
    private OrderItemDetailsAdapter orderItemDetails;
    private  TextView mTvSubmit,mEtPromocodeMsg;
    private RadioGroup mRadioGroup;
    ArrayList<TaxItems> taxItemses;
    private AppPreferences appPreferences;

    private EditText mEtPromoCodeNumber;
    private Button mBtApply;
    private TextView mTvBillSummary,mTvRestAddress,mTvBillDate,mTvBillNo,mTvOrderId,mTvOrderTotal;
    ArrayList<Order_Items> order_itemses;
    private Bill mBill;
    private  TextView mTvRestName;
    private  SqlOperations sqliteoperation;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);
        appPreferences=new AppPreferences(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Common.setCustomTitile(this,"View & Pay Bill",null);
        mRadioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        decalartion();
        init();
    }

    private void decalartion() {
        mTvRestName=(TextView)findViewById(R.id.tv_address_rest_names);
        mTvRestAddress=(TextView)findViewById(R.id.tv_address_rest_address);
        mTvBillDate=(TextView)findViewById(R.id.tv_bill_date);
        mTvBillNo=(TextView)findViewById(R.id.tv_bill_no);
        mTvOrderId=(TextView)findViewById(R.id.tv_order_id);
        mTvOrderTotal=(TextView)findViewById(R.id.tv_total_price);
        mTvBillSummary=(TextView)findViewById(R.id.tv_bill_table);
        mTvBillSummary.setText(getString(R.string.bill_summary));
        mEtPromoCodeNumber=(EditText)findViewById(R.id.et_promo_code);
        mEtPromocodeMsg=(TextView)findViewById(R.id.tv_price_of_total_msg);
        mBtApply=(Button)findViewById(R.id.bt_apply);
        mBtApply.setOnClickListener(this);
    }
    String currentDateTimeString;
    Long tsLong;
    private void init() {

        mLvItemQuantity=(ListView)findViewById(R.id.lv_items_list);
        mLvTaxQuality=(ListView)findViewById(R.id.lv_tax_menu);
        mTvSubmit=(TextView)findViewById(R.id.tv_submit);
        mTvSubmit.setOnClickListener(this);

        taxItemses = new ArrayList<TaxItems>();
        order_itemses=new ArrayList<Order_Items>();
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest stringRequest = new StringRequest(bill_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hidePDialog();
                        Gson gson=new Gson();
                        mBill = gson.fromJson(response, Bill.class);
                        setVaues();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(View_Pay_BillActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
        AppController.getInstance().addToRequestQueue(stringRequest);


       /* Restaurant restaurant=new Restaurant(1,"Restaurant name One", "39,Lime St", "Sydney NSW 2000", "Australia");
        Bill bill=new Bill(restaurant,order_itemses,taxItemses);
        Gson gson=new Gson();
        String json=gson.toJson(bill);*/
    }

    private void setVaues() {
        order_itemses= mBill.getOrderItems();
        mTvOrderId.setText(": "+mBill.getOrder_id());
        mTvBillDate.setText(": "+mBill.getBill_date());
        mTvBillNo.setText(": "+mBill.getBill_no());
        mTvRestName.setText(mBill.getAddress().getRestName());
        mTvRestAddress.setText(mBill.getAddress().getRestAdress()+", "+mBill.getAddress().getCity()+", "+mBill.getAddress().getCountry());



        orderItemDetails=new OrderItemDetailsAdapter(this,R.layout.order_summary_items,order_itemses);
        mLvItemQuantity.setAdapter(orderItemDetails);


        taxItemses=mBill.getTaxItems();
        double sum=0;
        for (int i = 0; i < taxItemses.size(); i++) {
            sum+=taxItemses.get(i).getValue();
        }

        mTvOrderTotal.setText("$ "+sum);
        taxDetailsAdapter=new TaxDetailsAdapter(this,R.layout.tax_items,taxItemses);
        mLvTaxQuality.setAdapter(taxDetailsAdapter);
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    private ArrayList<OrderedItemSummary> orderedItemSummaries;
    float totalbyOrder = 0;
    float totalNumberOfItems = 0;
    String item_cata;
    String order_id=null;
    List<String> order_ids;
    private ArrayList<Order_Items> getOrderHistory() {
        order_itemses=new ArrayList<Order_Items>();
        orderedItemSummaries= new ArrayList<OrderedItemSummary>();
        sqliteoperation = new SqlOperations(getApplicationContext());
        sqliteoperation.open();
         order_ids=sqliteoperation.getPayItems();
        List<List<HashMap<String, String>>> dictionary= new ArrayList<List<HashMap<String, String>>>();
       for(int i=0;i<order_ids.size();i++) {
           dictionary.add(sqliteoperation.getPlaceOrderItems(order_ids.get(i)));
       }
        sqliteoperation.close();
        int item_cat;
        int item_food;
        String totalbyFood;
        String quantity;
        String foodName;
        String messageOrder;
        String price;
        messageOrder = "\nOrder\nYour ordered";


        int j;

        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < dictionary.size(); i++) {


            /*I start at index 0 and finish at the penultimate index */
            List<HashMap<String, String>> maps = dictionary.get(i); //Get the corresponding map from the index
             for(int k=0;k<maps.size();k++){
                j = k + 1;
                HashMap<String, String> map=maps.get(k);
            item_cat= Integer.parseInt(map.get("index_category"));
            item_food= Integer.parseInt(map.get("index_food"));
            totalbyFood = map.get("totalByFood");
            price = map.get("price");
            quantity = map.get("quantity");
            foodName = map.get("food_name");
            order_id = map.get("order_id");
            messageOrder += "\n " + j + " - " + foodName + " (" + price + " $  x  " + quantity + ")  " + totalbyFood + "$";
            totalbyOrder += Float.parseFloat(totalbyFood);
            totalNumberOfItems += Float.parseFloat(quantity);
            item_cata=map.get("index_food");
            Order_Items order_items=new Order_Items(foodName,Float.parseFloat(price),Integer.parseInt(quantity));
            order_itemses.add(order_items);
            // OrderedItemSummary orderedItemSummary=new OrderedItemSummary(item_cat,item_food,foodName,Float.parseFloat(totalbyFood),Math.round(Float.parseFloat(quantity)),Float.parseFloat(price),serviceTax,vatTax,ser);
            //orderedItemSummaries.add(orderedItemSummary);
            JSONObject food = new JSONObject();
            try {
                food.put("totalByFood", totalbyFood);
                food.put("price", price);
                food.put("quantity", quantity);
                food.put("food_name", foodName);
            } catch (JSONException e) {
                Log.d("ViewCartActivity", e.toString());
                throw new RuntimeException(e);
            }
            jsonArray.put(food);
            }
        }
        return order_itemses;

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
    public void onClick(View v) {
    if (v.getId() == R.id.tv_submit){
        int selectedId = mRadioGroup.getCheckedRadioButtonId();
        if(selectedId != -1){
            totalbyOrder +=serviceTax+ser+vatTax;
            RadioButton radioButton = (RadioButton) mRadioGroup.findViewById(selectedId);
            Toast.makeText(this, radioButton.getText(), Toast.LENGTH_SHORT).show();
            sqliteoperation.open();
            for(int i=0;i<order_ids.size();i++) {
                sqliteoperation.updatePlaceOrderStatus(order_ids.get(i));
            }
            sqliteoperation.pymentStatment(appPreferences.getTABLE_NAME(),currentDateTimeString,appPreferences.getRESTAURANT_NAME(),order_id,appPreferences.getTABLE_NAME(),"Dinner",tsLong,totalbyOrder,"Received");
            sqliteoperation.close();

            Intent intent=new Intent(this,PaymentConfirmationActivity.class);
            intent.putExtra("bill_no",tsLong);
            startActivity(intent);
             //   mSelectDbHelper.updateOrderHistory(mOrderId,comments, (String) radioButton.getText());

        }else {
            Toast.makeText(this, "select one payment Type", Toast.LENGTH_SHORT).show();
        }

    }else  if(v.getId() == R.id.bt_apply){
        String promoCode=mEtPromoCodeNumber.getText().toString().trim();
        if(promoCode.length()>5)
            mEtPromocodeMsg.setText("Promo code not available");
        else
            promocodeServerSending();
            mEtPromocodeMsg.setText("Promo code  available");
    }

    }

    private void promocodeServerSending() {

    }

}