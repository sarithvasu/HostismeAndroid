package com.effone.hostismeandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.adapter.OrderItemDetailsAdapter;
import com.effone.hostismeandroid.adapter.TaxDetailsAdapter;
import com.effone.hostismeandroid.common.Common;
import com.effone.hostismeandroid.db.SqlOperations;
import com.effone.hostismeandroid.model.OrderSummary;
import com.effone.hostismeandroid.model.Order_Items;
import com.effone.hostismeandroid.model.OrderedItemSummary;
import com.effone.hostismeandroid.model.TaxItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.effone.hostismeandroid.activity.ViewCartActivity.ser;
import static com.effone.hostismeandroid.activity.ViewCartActivity.serviceTax;
import static com.effone.hostismeandroid.activity.ViewCartActivity.vatTax;

/**
 * Created by sumanth.peddinti on 4/17/2017.
 */

public class ConfirmationActivity extends AppCompatActivity {
    private  TextView mTvDateTime,mTvRestName,mTvBookingId,mTvDescription,mTvTableNo,mTvQuantits,mTvOrderTotal,mTvStatus,mTvTotalPrice;
    private OrderSummary orderSummary;
    private ListView mLvItemQuantity,mLvTaxQuality;
    private TaxDetailsAdapter taxDetailsAdapter;
    private OrderItemDetailsAdapter orderItemDetails;
    ArrayList<TaxItems> taxItemses;
    private RelativeLayout mRelativeLayout;
    ArrayList<Order_Items> order_itemses;
    private AppBarLayout mAppBarToolBar;
    private  SqlOperations sqliteoperation;
    private ListView mListView;
    private String order_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confiramtion);
        Intent intent=getIntent();
        order_id=intent.getStringExtra("Order_id");
        sqliteoperation = new SqlOperations(getApplicationContext());
        sqliteoperation.open();
        List<HashMap<String, String>> dictionary = sqliteoperation.getPlaceOrder(order_id);
        sqliteoperation.close();
        HashMap<String, String> map=dictionary.get(0);
        orderSummary= new OrderSummary(map.get("time_stamp"),map.get("rest_name"),map.get("_id"),
                map.get("description"),Integer.parseInt(map.get("table_no")),Integer.parseInt(map.get("quantity")),Float.parseFloat(map.get("totalPrice")),map.get("status"));
        mAppBarToolBar=(AppBarLayout)findViewById(R.id.titel_na);
        mAppBarToolBar.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Common.setCustomTitile(this,"Order Confirmation",null);
        init();

      // sqliteoperation.updatetheCart();
    }


    private void init() {
        mLvItemQuantity=(ListView)findViewById(R.id.lv_items_list);
        mLvTaxQuality=(ListView)findViewById(R.id.lv_tax_menu);
        mListView=(ListView)findViewById(R.id.historyView);
        mListView.setVisibility(View.GONE);
        mRelativeLayout=(RelativeLayout)findViewById(R.id.relativeLayout);
        mRelativeLayout.setBackground(getDrawable(R.drawable.payment_background));
        mTvDateTime=(TextView)findViewById(R.id.tv_data_time);
        mTvRestName=(TextView)findViewById(R.id.tv_rest_name);
        mTvBookingId=(TextView)findViewById(R.id.tv_booking_id);
        mTvDescription=(TextView)findViewById(R.id.tv_descrption);
        mTvTableNo=(TextView)findViewById(R.id.tv_table_no);
        mTvQuantits=(TextView)findViewById(R.id.tv_quantitys);
        mTvOrderTotal=(TextView)findViewById(R.id.tv_order_total);
        mTvStatus=(TextView)findViewById(R.id.tv_order_status);
        mTvTotalPrice=(TextView)findViewById(R.id.tv_total_price);
        settingValues();
    }

    private void settingValues() {
        mTvDateTime.setText(": "+orderSummary.getData_time());
        mTvRestName.setText(": "+orderSummary.getRest_name());
        mTvBookingId.setText(": "+orderSummary.getOrder_id());
        mTvDescription.setText(": "+orderSummary.getDescription());
        mTvTableNo.setText(": "+orderSummary.getTable_no());
        mTvQuantits.setText(": "+orderSummary.getQuantity());
        mTvOrderTotal.setText(": $ "+orderSummary.getTotal());
        mTvStatus.setText(orderSummary.getStatus());

        taxItemses = new ArrayList<TaxItems>();
       /* order_itemses=new ArrayList<Order_Items>();
        //adding oreder_items:
        Order_Items order_items=new Order_Items("Olives Warm Marinated",10,10);
        Order_Items order_items1=new Order_Items("Bread Fresh Baked",10,9);
        order_itemses.add(order_items);
        order_itemses.add(order_items1);*/


        order_itemses= getOrderHistory();

        TaxItems res1 = new TaxItems("Total before Tax", totalbyOrder);
        TaxItems res2 = new TaxItems("Service Charges", serviceTax);
        TaxItems res3 = new TaxItems("Service Tax",vatTax);
        TaxItems res4 = new TaxItems("VAT Tax", ser);
        taxItemses.add(res1);
        taxItemses.add(res2);
        taxItemses.add(res3);
        taxItemses.add(res4);

        orderItemDetails=new OrderItemDetailsAdapter(this,R.layout.order_summary_items,order_itemses);
        mLvItemQuantity.setAdapter(orderItemDetails);

        taxDetailsAdapter=new TaxDetailsAdapter(this,R.layout.tax_items,taxItemses);
        mLvTaxQuality.setAdapter(taxDetailsAdapter);
        double sum=totalbyOrder+serviceTax+ser+vatTax;
        mTvTotalPrice.setText("$ "+sum);

    }
    private ArrayList<OrderedItemSummary> orderedItemSummaries;
    float totalbyOrder = 0;
    float totalNumberOfItems = 0;
    String item_cata;
    private ArrayList<Order_Items> getOrderHistory() {
        order_itemses=new ArrayList<Order_Items>();
            orderedItemSummaries= new ArrayList<OrderedItemSummary>();
            sqliteoperation = new SqlOperations(getApplicationContext());
            sqliteoperation.open();
            List<HashMap<String, String>> dictionary = sqliteoperation.getOrder(order_id);
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

                    j = i + 1;
            /*I start at index 0 and finish at the penultimate index */
                    HashMap<String, String> map = dictionary.get(i); //Get the corresponding map from the index
                    item_cat = Integer.parseInt(map.get("index_category"));
                    item_food = Integer.parseInt(map.get("index_food"));
                    totalbyFood = map.get("totalByFood");
                    price = map.get("price");
                    quantity = map.get("quantity");
                    foodName = map.get("food_name");
                    messageOrder += "\n " + j + " - " + foodName + " (" + price + " $  x  " + quantity + ")  " + totalbyFood + "$";
                    totalbyOrder += Float.parseFloat(totalbyFood);
                    totalNumberOfItems += Float.parseFloat(quantity);
                    item_cata = map.get("index_food");
                    Order_Items order_items = new Order_Items(foodName, Float.parseFloat(price), Integer.parseInt(quantity));
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

        return order_itemses;

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
