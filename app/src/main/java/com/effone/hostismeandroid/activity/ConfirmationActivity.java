package com.effone.hostismeandroid.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.adapter.OrderItemDetailsAdapter;
import com.effone.hostismeandroid.adapter.TaxDetailsAdapter;
import com.effone.hostismeandroid.model.OrderSummary;
import com.effone.hostismeandroid.model.Order_Items;
import com.effone.hostismeandroid.model.TaxItems;

import java.util.ArrayList;

/**
 * Created by sumanth.peddinti on 4/17/2017.
 */

public class ConfirmationActivity extends AppCompatActivity {
    private  TextView mTvDateTime,mTvRestName,mTvBookingId,mTvDescription,mTvTableNo,mTvQuantits,mTvOrderTotal,mTvStatus;
    private OrderSummary orderSummary;
    private ListView mLvItemQuantity,mLvTaxQuality;
    private TaxDetailsAdapter taxDetailsAdapter;
    private OrderItemDetailsAdapter orderItemDetails;
    ArrayList<TaxItems> taxItemses;
    private RelativeLayout mRelativeLayout;
    ArrayList<Order_Items> order_itemses;
    private AppBarLayout mAppBarToolBar;
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confiramtion);
        orderSummary= new OrderSummary("12 Apr 2017 – 07:30 PM","Restaurant Name One","SY56002019924","Dinner",99,20,246.0,"BOOKED");
        mAppBarToolBar=(AppBarLayout)findViewById(R.id.titel_na);
        mAppBarToolBar.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Payment");
       // getSupportActionBar().setTitle(getString(R.string.booking_history));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        init();
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

        settingValues();
    }

    private void settingValues() {
        mTvDateTime.setText(orderSummary.getData_time());
        mTvRestName.setText(orderSummary.getRest_name());
        mTvBookingId.setText(orderSummary.getOrder_id());
        mTvDescription.setText(orderSummary.getDescription());
        mTvTableNo.setText(""+orderSummary.getTable_no());
        mTvQuantits.setText(""+orderSummary.getQuantity());
        mTvOrderTotal.setText(""+orderSummary.getTotal());
        mTvStatus.setText(orderSummary.getStatus());

        taxItemses = new ArrayList<TaxItems>();
        order_itemses=new ArrayList<Order_Items>();
        //adding oreder_items:
        Order_Items order_items=new Order_Items("Olives Warm Marinated",10,10);
        Order_Items order_items1=new Order_Items("Bread Fresh Baked",10,9);
        order_itemses.add(order_items);
        order_itemses.add(order_items1);

        TaxItems res1 = new TaxItems(" Total before Tax", 200);
        TaxItems res2 = new TaxItems("Service Charges", 200);
        TaxItems res3 = new TaxItems("Service Tax",200);
        TaxItems res4 = new TaxItems("VAT Tax", 200);
        taxItemses.add(res1);
        taxItemses.add(res2);
        taxItemses.add(res3);
        taxItemses.add(res4);

        orderItemDetails=new OrderItemDetailsAdapter(this,R.layout.order_summary_items,order_itemses);
        mLvItemQuantity.setAdapter(orderItemDetails);

        taxDetailsAdapter=new TaxDetailsAdapter(this,R.layout.tax_items,taxItemses);
        mLvTaxQuality.setAdapter(taxDetailsAdapter);

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
