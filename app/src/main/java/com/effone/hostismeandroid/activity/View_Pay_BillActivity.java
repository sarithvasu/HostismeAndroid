package com.effone.hostismeandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.adapter.OrderItemDetailsAdapter;
import com.effone.hostismeandroid.adapter.TaxDetailsAdapter;
import com.effone.hostismeandroid.common.Common;
import com.effone.hostismeandroid.model.Order_Items;
import com.effone.hostismeandroid.model.TaxItems;


import org.w3c.dom.Text;

import java.util.ArrayList;

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
    ArrayList<Order_Items> order_itemses;
    private EditText mEtPromoCodeNumber;
    private Button mBtApply;
    private TextView mTvBillSummary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);
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
        mTvBillSummary=(TextView)findViewById(R.id.tv_bill_table);
        mTvBillSummary.setText(getString(R.string.bill_summary));
        mEtPromoCodeNumber=(EditText)findViewById(R.id.et_promo_code);
        mEtPromocodeMsg=(TextView)findViewById(R.id.tv_price_of_total_msg);
        mBtApply=(Button)findViewById(R.id.bt_apply);
        mBtApply.setOnClickListener(this);
    }

    private void init() {

        mLvItemQuantity=(ListView)findViewById(R.id.lv_items_list);
        mLvTaxQuality=(ListView)findViewById(R.id.lv_tax_menu);
        mTvSubmit=(TextView)findViewById(R.id.tv_submit);
        mTvSubmit.setOnClickListener(this);

        taxItemses = new ArrayList<TaxItems>();
        order_itemses=new ArrayList<Order_Items>();
        //adding oreder_items:
        Order_Items order_items=new Order_Items("Olives Warm Marinated",10,10);
        Order_Items order_items1=new Order_Items("Bread Fresh Baked",10,9);
        order_itemses.add(order_items);
        order_itemses.add(order_items1);


        orderItemDetails=new OrderItemDetailsAdapter(this,R.layout.order_summary_items,order_itemses);
        mLvItemQuantity.setAdapter(orderItemDetails);


        TaxItems res1 = new TaxItems("Total before Tax", 200);
        TaxItems res2 = new TaxItems("Service Charges", 200);
        TaxItems res3 = new TaxItems("Service Tax",200);
        TaxItems res4 = new TaxItems("VAT Tax", 200);
        taxItemses.add(res1);
        taxItemses.add(res2);
        taxItemses.add(res3);
        taxItemses.add(res4);
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

    @Override
    public void onClick(View v) {
    if (v.getId() == R.id.tv_submit){
        int selectedId = mRadioGroup.getCheckedRadioButtonId();
        if(selectedId != -1){

            RadioButton radioButton = (RadioButton) mRadioGroup.findViewById(selectedId);
            Toast.makeText(this, radioButton.getText(), Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,PaymentConfirmationActivity.class);
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
            mEtPromocodeMsg.setText("Promo code  available");
    }

    }

}