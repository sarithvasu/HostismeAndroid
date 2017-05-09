package com.effone.hostismeandroid.activity;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.common.AppPreferences;
import com.effone.hostismeandroid.common.Common;
import com.effone.hostismeandroid.db.SqlOperations;
import com.effone.hostismeandroid.db.SqliteConnection;
import com.effone.hostismeandroid.model.BookingHistoryItem;
import com.effone.hostismeandroid.model.OrderSummary;

import java.util.HashMap;
import java.util.List;

public class PaymentConfirmationActivity extends AppCompatActivity {

    private TextView mTvDateTime, mTvRestName, mTvBookingId, mTvDescription, mTvTableNo, mTvQuantits, mTvOrderTotal, mTvStatus,mTvHeadingText;
    private ListView mListView;
    private AppBarLayout mAppBarLayout;
    private RelativeLayout mRelativeLayout;
    private SqlOperations sqliteoperation;
    private AppPreferences appPreferences;
    Long bill_no;
    List<BookingHistoryItem> mBookingHistoryItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);
        Intent intent=getIntent();
        bill_no=intent.getLongExtra("bill_no",0);
        sqliteoperation = new SqlOperations(getApplicationContext());
        sqliteoperation.open();
        appPreferences=new AppPreferences(this);
        appPreferences.setRRESTAURANT_NAME(null);
        appPreferences.setTABLE_NAME(0);
        mBookingHistoryItem = sqliteoperation.getBookedHistory(bill_no);
        sqliteoperation.close();
       // orderSummary = new OrderSummary(, ": Restaurant Name One", ": SY56002019924", ": Dinner", 99, 20, 246.0, ": Received");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Common.setCustomTitile(this,"Payment Confirmation",null);
        init();


    }

    private void init() {
        mTvHeadingText=(TextView)findViewById(R.id.tv_order_Summery);
        mTvHeadingText.setText("Pyment Summary");
        LinearLayout mRelativLayout=(LinearLayout)findViewById(R.id.relativeLayout);
        mRelativLayout.setBackground(getDrawable(R.drawable.payment_background));

        mTvDateTime = (TextView) findViewById(R.id.tv_data_time);
        mTvRestName = (TextView) findViewById(R.id.tv_rest_name);
        mTvBookingId = (TextView) findViewById(R.id.tv_booking_id);
        mTvDescription = (TextView) findViewById(R.id.tv_decription);
        mTvTableNo = (TextView) findViewById(R.id.tv_table_no);
        mTvQuantits = (TextView) findViewById(R.id.tv_bill_ammount);
        mTvOrderTotal = (TextView) findViewById(R.id.tv_order_total);
        mTvStatus = (TextView) findViewById(R.id.tv_order_status);

        settingValues();
    }

    private void settingValues() {
        mTvDateTime.setText(": "+mBookingHistoryItem.get(0).getDate());
        mTvRestName.setText(": "+mBookingHistoryItem.get(0).getRest_name());
        mTvBookingId.setText(": "+mBookingHistoryItem.get(0).getOrder_id());
        mTvDescription.setText(": "+mBookingHistoryItem.get(0).getDescription());
        mTvTableNo.setText(": " + mBookingHistoryItem.get(0).getTable_no());
        //mTvQuantits.setText(": $ " + mBookingHistoryItem.get(0).getBill_ammount());
        mTvOrderTotal.setText(": " + mBookingHistoryItem.get(0).getBill_ammount());
        mTvStatus.setText(mBookingHistoryItem.get(0).getStatus());

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