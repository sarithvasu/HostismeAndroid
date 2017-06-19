package com.effone.hostismeandroid.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.effone.hostismeandroid.MainActivity;
import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.app.AppController;
import com.effone.hostismeandroid.common.AppPreferences;
import com.effone.hostismeandroid.common.Common;
import com.effone.hostismeandroid.db.SqlOperations;
import com.effone.hostismeandroid.model.PaymentConfirmation;
import com.effone.hostismeandroid.util.Util;
import com.google.gson.Gson;

import static com.effone.hostismeandroid.common.URL.book_a_table_url;
import static com.effone.hostismeandroid.common.URL.payment_confirmation_url;

public class PaymentConfirmationActivity extends AppCompatActivity {

    private TextView mTvDateTime, mTvRestName, mTvBookingId, mTvDescription, mTvTableNo, mTvQuantits, mTvOrderTotal, mTvStatus,mTvHeadingText;
    private ListView mListView;
    private AppBarLayout mAppBarLayout;
    private RelativeLayout mRelativeLayout;
    private SqlOperations sqliteoperation;
    private AppPreferences appPreferences;
    Long bill_no;
    PaymentConfirmation mPaymentConfirmation;
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
       /* mPaymentConfirmation = sqliteoperation.getBookedHistory(bill_no);
        sqliteoperation.close();*/
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
        mTvHeadingText.setText("Payment Summary");
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
    ProgressDialog pDialog;
    private void settingValues() {
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        final Gson gson=new Gson();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, payment_confirmation_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mPaymentConfirmation=gson.fromJson(response,PaymentConfirmation.class);
                        mTvDateTime.setText(": "+ mPaymentConfirmation.getBill_date());
                        mTvRestName.setText(": "+ mPaymentConfirmation.getRestName());
                        mTvBookingId.setText(": "+ mPaymentConfirmation.getOrder_id());
                        mTvDescription.setText(": "+ mPaymentConfirmation.getDescription());
                        mTvTableNo.setText(": " + mPaymentConfirmation.getTable_no());
                        //mTvQuantits.setText(": $ " + mPaymentConfirmation.get(0).getBill_ammount());
                        mTvOrderTotal.setText(": " + mPaymentConfirmation.getBill_ammount());
                        mTvStatus.setText(": " +mPaymentConfirmation.getStatus());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hidePDialog();
                        Util.createErrorAlert(PaymentConfirmationActivity.this, "", error.getMessage());
                    }
                });
        AppController.getInstance().addToRequestQueue(stringRequest);


    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
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