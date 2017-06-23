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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.effone.hostismeandroid.MainActivity;
import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.adapter.OrderItemDetailsAdapter;
import com.effone.hostismeandroid.adapter.RestaurantListAdapter;
import com.effone.hostismeandroid.adapter.TaxDetailsAdapter;
import com.effone.hostismeandroid.app.AppController;
import com.effone.hostismeandroid.common.AppPreferences;
import com.effone.hostismeandroid.common.Common;
import com.effone.hostismeandroid.db.SqlOperations;

import com.effone.hostismeandroid.model.Order_Items;
import com.effone.hostismeandroid.model.OrderedItemSummary;
import com.effone.hostismeandroid.model.Restaurant;
import com.effone.hostismeandroid.model.TaxItems;
import com.effone.hostismeandroid.model_for_json.Bill;
import com.effone.hostismeandroid.model_for_json.Order;
import com.effone.hostismeandroid.model_for_json.OrderItem;
import com.effone.hostismeandroid.model_for_json.TaxItem;
import com.effone.hostismeandroid.util.Util;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.effone.hostismeandroid.activity.ConfirmationActivity.setListViewHeightBasedOnItems;
import static com.effone.hostismeandroid.activity.ViewCartActivity.ser;
import static com.effone.hostismeandroid.activity.ViewCartActivity.serviceTax;
import static com.effone.hostismeandroid.activity.ViewCartActivity.vatTax;
import static com.effone.hostismeandroid.common.URL.APPLY_PROMOCODE;
import static com.effone.hostismeandroid.common.URL.GET_BILL;
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
    private TextView mTvBillSummary,mTvRestAddress,mTvBillDate,mTvBillNo,mTvOrderId,mTvOrderTotal,mTvDiscount;
    ArrayList<OrderItem> order_itemses;
    private Bill mBill;
    private  TextView mTvRestName;
    /*private  SqlOperations sqliteoperation;*/
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
        mTvDiscount=(TextView) findViewById(R.id.tv_discount_price_bill);
        mEtPromocodeMsg=(TextView)findViewById(R.id.tv_price_of_total_msg);
        mBtApply=(Button)findViewById(R.id.bt_apply);
        mBtApply.setOnClickListener(this);
    }

    Long tsLong;

    private void init() {

        mLvItemQuantity=(ListView)findViewById(R.id.lv_items_list);
        mLvTaxQuality=(ListView)findViewById(R.id.lv_tax_menu);
        mTvSubmit=(TextView)findViewById(R.id.tv_submit);
        mTvSubmit.setOnClickListener(this);

        taxItemses = new ArrayList<>();
        order_itemses=new ArrayList<OrderItem>();
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        /*save order id and restaurant id in preference*/
        String url=GET_BILL+"?order_id="+appPreferences.getORDER_ID()+"&restaurant_id="+appPreferences.getRESTAURANT_ID()+"&deviceid="+appPreferences.getDEVICE_ID()+"&tableno="+appPreferences.getTABLE_NAME();
      //  String Testurl="http://192.168.11.65/hostisme/admin/api/viewandpaybill/get-billdetails?order_id=1&restaurant_id=1&deviceid=14558295348432156&tableno=9999";
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hidePDialog();
                        try {
                            JSONObject jsonObjec = new JSONObject(response);
                            boolean status = jsonObjec.getBoolean("status");

                            if (status) {
                                Gson gson = new Gson();
                                mBill = gson.fromJson(jsonObjec.getString("Bill"), Bill.class);
                                JSONObject os=jsonObjec.getJSONObject("Bill");
                               getingtax(os);

                                setVaues();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            Util.createOKAlert(View_Pay_BillActivity.this,  "", e.getMessage());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Util.createOKAlert(View_Pay_BillActivity.this,  "", error.getMessage());

                    }
                });
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
/*getting text item since Tax name came in json as key "taxItems": [
            {
                "Total before Tax": 2490,
                "Tax1": 100,
                "Tax2": 124.5,
                "Tax3": 200,
                "Tax4": 249
            }
        ]*/
    private void getingtax(JSONObject os) {
        try {
            JSONArray json = os.getJSONArray("taxItems");
            JSONObject object = json.getJSONObject(0);
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            try {
                Iterator<String> iterator = object.keys();

                while (iterator.hasNext()) {
                    String key = iterator.next();
                    double value = Double.parseDouble(object.getString(key));
                    TaxItems taxitem=new TaxItems();
                    taxitem.setName(key);
                    taxitem.setValue(value);

                    taxItemses.add(taxitem);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setVaues() {
        order_itemses= (ArrayList<OrderItem>) mBill.getOrderItems();
        mTvOrderId.setText(": "+appPreferences.getORDER_ID());
        mTvBillDate.setText(": "+mBill.getBilldetails().getBill_date());
        mTvBillNo.setText(": "+mBill.getBilldetails().getBillno());
        /*add restuarent address to preference*/

        mTvRestName.setText(""+appPreferences.getRESTAURANT_NAME());
        mTvRestAddress.setText(""+appPreferences.getRESTAURANT_ADDRESS());



        orderItemDetails=new OrderItemDetailsAdapter(this,R.layout.order_summary_items,order_itemses);
        mLvItemQuantity.setAdapter(orderItemDetails);
        setListViewHeightBasedOnItems(mLvItemQuantity);

      //  getTaxItems();

        mTvOrderTotal.setText("$ "+mBill.getBilldetails().getOrderTotal());
        taxDetailsAdapter=new TaxDetailsAdapter(this,R.layout.tax_items,
                taxItemses);
        mLvTaxQuality.setAdapter(taxDetailsAdapter);
        setListViewHeightBasedOnItems(mLvTaxQuality);
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
            /*sqliteoperation=new SqlOperations(View_Pay_BillActivity.this);
            sqliteoperation.open();
            for(int i=0;i<order_ids.size();i++) {
                sqliteoperation.updatePlaceOrderStatus(order_ids.get(i));
            }
            sqliteoperation.pymentStatment(appPreferences.getTABLE_NAME(),currentDateTimeString,appPreferences.getRESTAURANT_NAME(),order_id,appPreferences.getTABLE_NAME(),"Dinner",tsLong,totalbyOrder,"Received");
            sqliteoperation.close();*/

         /*   Intent intent=new Intent(this,PaymentConfirmationActivity.class);
            intent.putExtra("bill_no",tsLong);
            startActivity(intent);*/
             //   mSelectDbHelper.updateOrderHistory(mOrderId,comments, (String) radioButton.getText());

        }else {
            Util.createErrorAlert(View_Pay_BillActivity.this, "", "select one payment Type");
        }

    }else  if(v.getId() == R.id.bt_apply){
        String promoCode=mEtPromoCodeNumber.getText().toString().trim();
        if(promoCode.length() == 10)
            promocodeServerSending(promoCode);
        else
            mEtPromocodeMsg.setText("Promo code not available");
    }

    }

    private void promocodeServerSending(String promoCode) {
        String url=APPLY_PROMOCODE+"?restaurant_id="+appPreferences.getRESTAURANT_ID()+"&promocode="+promoCode+"&device_id="+appPreferences.getDEVICE_ID();
        StringRequest stringRequest=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObjec = new JSONObject(response);
                    String status=jsonObjec.getString("status");
                    double ammount=0;
                    if(jsonObjec.getString("discount_amount").equals("")){
                    ammount=0;
                    } else{
                        ammount = Double.parseDouble(jsonObjec.getString("discount_amount"));
                    }
                    Util.createErrorAlert(View_Pay_BillActivity.this, "", status);
                    double amounts=Double.parseDouble(mBill.getBilldetails().getOrderTotal())- ammount;
                    mTvOrderTotal.setText("$ "+amounts);
                    mEtPromocodeMsg.setText(status);
                    mTvDiscount.setText("$ "+ammount);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.createErrorAlert(View_Pay_BillActivity.this, "", error.getMessage());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}