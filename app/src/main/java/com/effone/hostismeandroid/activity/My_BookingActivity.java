package com.effone.hostismeandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.adapter.Booking_HistoryAdapter;
import com.effone.hostismeandroid.common.AppPreferences;
import com.effone.hostismeandroid.common.Common;
import com.effone.hostismeandroid.db.SqlOperations;
import com.effone.hostismeandroid.model.BookingHistoryItem;
import com.effone.hostismeandroid.model.OrderSummary;
import com.effone.hostismeandroid.model.Order_Items;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarith.vasu on 14-04-2017.
 */

public class My_BookingActivity extends AppCompatActivity {
    private AppPreferences appPreferences;
    private  ListView mLvBookingHistory;
    private Booking_HistoryAdapter bookingHistoryAdapter;
    private ArrayList<OrderSummary> orderSummaries;
    private RelativeLayout mRelativeLayout;
    private SqlOperations sqliteoperation;
    Long bill_no= Long.valueOf(000000);
    ArrayList<BookingHistoryItem> mBookingHistoryItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_history_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        sqliteoperation = new SqlOperations(getApplicationContext());
        sqliteoperation.open();
        mBookingHistoryItem = (ArrayList<BookingHistoryItem>) sqliteoperation.getBookedHistory(bill_no);
        sqliteoperation.close();
        appPreferences=new AppPreferences(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Common.setCustomTitile(this,"Booking History",null);
      /*  if(appPreferences.getREST_NAME()!= null)
        {

        }else {
            toolbar.setTitle(""+appPreferences.getREST_NAME());

        }*/
        init();
    }

    private void init() {
        mRelativeLayout=(RelativeLayout)findViewById(R.id.relativeLayout);
        mRelativeLayout.setVisibility(View.GONE);
        mLvBookingHistory=(ListView)findViewById(R.id.historyView);
        virtualMethod();
        bookingHistoryAdapter=new Booking_HistoryAdapter(this,R.layout.booking_history_items,mBookingHistoryItem);
        mLvBookingHistory.setAdapter(bookingHistoryAdapter);
    }

    private void virtualMethod() {
        orderSummaries=new ArrayList<OrderSummary>();
        OrderSummary order_items=new OrderSummary("11 Apr 2017-14:30PM","Restaurant Name One","Sy56002019924","",45, 0 ,246.00,"Booked");
        OrderSummary order_items1=new OrderSummary("12 Apr 2017-14:30PM","Restaurant Name One","Sy56002019924","",45, 0 ,246.00,"Booked");
        OrderSummary order_items2=new OrderSummary("13 Apr 2017-14:30PM","Restaurant Name One","Sy56002019924","",45, 0 ,246.00,"Booked");

        orderSummaries.add(order_items);
        orderSummaries.add(order_items1);
        orderSummaries.add(order_items2);
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
