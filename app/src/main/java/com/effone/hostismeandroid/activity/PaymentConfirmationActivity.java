package com.effone.hostismeandroid.activity;

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
import com.effone.hostismeandroid.common.Common;
import com.effone.hostismeandroid.model.OrderSummary;

public class PaymentConfirmationActivity extends AppCompatActivity {

    private TextView mTvDateTime, mTvRestName, mTvBookingId, mTvDescription, mTvTableNo, mTvQuantits, mTvOrderTotal, mTvStatus,mTvHeadingText;
    private ListView mListView;
    private OrderSummary orderSummary;
    private AppBarLayout mAppBarLayout;
    private RelativeLayout mRelativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);
        orderSummary = new OrderSummary(": 12 Apr 2017 – 07:30 PM", ": Restaurant Name One", ": SY56002019924", ": Dinner", 99, 20, 246.0, ": Received");
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
        mTvDateTime.setText(orderSummary.getData_time());
        mTvRestName.setText(orderSummary.getRest_name());
        mTvBookingId.setText(orderSummary.getOrder_id());
        mTvDescription.setText(orderSummary.getDescription());
        mTvTableNo.setText(": " + orderSummary.getTable_no());
        mTvQuantits.setText(": $ " + orderSummary.getTotal());
        mTvOrderTotal.setText(": " + orderSummary.getQuantity());
        mTvStatus.setText(orderSummary.getStatus());

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