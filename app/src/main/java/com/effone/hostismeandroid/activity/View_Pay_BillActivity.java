package com.effone.hostismeandroid.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
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
import com.effone.hostismeandroid.common.UnScrollListView;
import com.effone.hostismeandroid.db.SqlOperations;

import com.effone.hostismeandroid.model.Order_Items;
import com.effone.hostismeandroid.model.OrderedItemSummary;
import com.effone.hostismeandroid.model.Restaurant;
import com.effone.hostismeandroid.model.TaxItems;
import com.effone.hostismeandroid.model_for_json.Bill;
import com.effone.hostismeandroid.model_for_json.Order;
import com.effone.hostismeandroid.model_for_json.OrderItem;
import com.effone.hostismeandroid.model_for_json.Payment;
import com.effone.hostismeandroid.model_for_json.PaymentJson;
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
import java.util.Map;
import java.util.Set;

import static com.effone.hostismeandroid.activity.ConfirmationActivity.setListViewHeightBasedOnItems;

import static com.effone.hostismeandroid.common.URL.APPLY_PROMOCODE;
import static com.effone.hostismeandroid.common.URL.GET_BILL;
import static com.effone.hostismeandroid.common.URL.POST_ORDER;
import static com.effone.hostismeandroid.common.URL.POST_PAYMENT;
import static com.effone.hostismeandroid.common.URL.bill_url;

/**
 * Created by sumanth.peddinti on 4/13/2017.
 */

public class View_Pay_BillActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvSelectedDate;
    private UnScrollListView mLvItemQuantity,mLvTaxQuality;
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
    private PaymentJson mPaymentJson;
    private Payment mPayment;
    private String mStringPaymentJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);
        appPreferences=new AppPreferences(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Common.setCustomTitile(this,getString(R.string.view_pay_bill),null);
        mRadioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        if (!Util.Operations.isOnline(this)) {
            Intent intent = new Intent(getApplicationContext(), NoNetworkActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else {
            decalartion();
            init();
        }
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
        mPaymentJson=new PaymentJson();
        mPayment=new Payment();
        mPayment.setDevice_id(appPreferences.getDEVICE_ID());
        mPayment.setOrder_id(appPreferences.getORDER_ID());

        mPayment.setOrderprice(""+orderAmount);
        mPayment.setDiscountprice("");
        mPayment.setOrderstatus("2");
        mPayment.setPhaseid(""+appPreferences.getPhaseId());//we need to save this form confrimation screem
        mPayment.setRestaurant_id(appPreferences.getRESTAURANT_ID());
        mPayment.setPromocode("");
        mPayment.setTableno(""+appPreferences.getTABLE_NAME());
        mPayment.setTax(""+taxAmmount);
        //mPayment.setTotalprice(""+mBill.getBilldetails().getOrderTotal());
        mPaymentJson.setPayment(mPayment);
        mBtApply.setOnClickListener(this);
    }

    Long tsLong;

    private void init() {

        mLvItemQuantity=(UnScrollListView) findViewById(R.id.lv_items_list);
        mLvTaxQuality=(UnScrollListView)findViewById(R.id.lv_tax_menu);
        mTvSubmit=(TextView)findViewById(R.id.tv_submit);
        mTvSubmit.setOnClickListener(this);

        taxItemses = new ArrayList<>();
        order_itemses=new ArrayList<OrderItem>();
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage(getString(R.string.loading));
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
                                mPayment.setTotalprice(""+mBill.getBilldetails().getOrderTotal());
                                JSONObject os=jsonObjec.getJSONObject("Bill");
                               getingtax(os);

                                setVaues();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            Util.createOKAlert(View_Pay_BillActivity.this,  "", e.getMessage()+getString(R.string.server_error));
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Util.createOKAlert(View_Pay_BillActivity.this,  "", error.getMessage()+getString(R.string.server_error));

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
    double orderAmount=0;
    Double taxAmmount=0.0;
    public void orderAmmount(){

        for (int i = 0; i < taxItemses.size(); i++) {
            if(i==0){
                orderAmount=taxItemses.get(i).getValue();
            }else {
                taxAmmount=taxAmmount+taxItemses.get(i).getValue();
            }
        }
    }


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
                orderAmmount();
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
        //setListViewHeightBasedOnItems(mLvItemQuantity);

      //  getTaxItems();

        mTvOrderTotal.setText("$ "+mBill.getBilldetails().getOrderTotal());
        taxDetailsAdapter=new TaxDetailsAdapter(this,R.layout.tax_items,
                taxItemses);
        mLvTaxQuality.setAdapter(taxDetailsAdapter);
        mLvItemQuantity.setExpanded(true);
        mLvTaxQuality.setExpanded(true);
      //  setListViewHeightBasedOnItems(mLvTaxQuality);
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
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                final View v = findViewById(R.id.home_btn);

                if (v != null) {
                    v.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            return false;
                        }
                    });
                }
            }
        });
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

            RadioButton radioButton = (RadioButton) mRadioGroup.findViewById(selectedId);
          //  paymentToServer();

          /* */
            mPayment.setDevice_id(appPreferences.getDEVICE_ID());
            mPayment.setOrder_id(appPreferences.getORDER_ID());

            mPayment.setOrderprice(""+orderAmount);
            /* order status 2 for booked*/
            mPayment.setOrderstatus("2");
            mPayment.setPhaseid(""+appPreferences.getPhaseId());//we need to save this form confrimation screem
            mPayment.setRestaurant_id(appPreferences.getRESTAURANT_ID());

            mPayment.setTableno(""+appPreferences.getTABLE_NAME());
            mPayment.setTax(""+taxAmmount);

            mPaymentJson.setPayment(mPayment);


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
            if (!Util.Operations.isOnline(this)) {
                Intent intent = new Intent(getApplicationContext(), NoNetworkActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            else
                paymentToServer();
        }else {
            Util.createErrorAlert(View_Pay_BillActivity.this, "", getString(R.string.select_payment_type));
        }

    }else  if(v.getId() == R.id.bt_apply){
        String promoCode=mEtPromoCodeNumber.getText().toString().trim();
        if(promoCode.length() == 10)
            promocodeServerSending(promoCode);
        else
            mEtPromocodeMsg.setText(getString(R.string.promo_not_avialble));
        mEtPromocodeMsg.setTextColor(Color.RED);
    }

    }

    private void paymentToServer() {
        mPaymentJson.setPayment(mPayment);
        Gson gson=new Gson();
        mStringPaymentJson=gson.toJson(mPaymentJson);
        StringRequest req = new StringRequest(Request.Method.POST, POST_PAYMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String value = response;
                        if (!value.equals("")) {
                            try {
                                JSONObject respObject=new JSONObject(value);
                               if(respObject.getBoolean("status")) {
                                   appPreferences.setRRESTAURANT_NAME("");
                                   appPreferences.setTABLE_NAME(0);
                                   appPreferences.setORDER_ID("");
                                   appPreferences.setNUMBER_OF_TABLES(0);
                                   appPreferences.setNUMBER_OF_TABLES(0);
                                   appPreferences.setFlag(false);
                                   finish();
                               }
                               else{
                                   Util.createOKAlert(View_Pay_BillActivity.this,"",getString(R.string.payment_failed));
                               }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.createOKAlert(View_Pay_BillActivity.this,"",error.getMessage()+getString(R.string.error_encounted));
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return mStringPaymentJson.getBytes();
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

    private void promocodeServerSending(final String promoCode) {

        String url=APPLY_PROMOCODE+"?restaurant_id="+appPreferences.getRESTAURANT_ID()+"&promocode="+promoCode+"&device_id="+appPreferences.getDEVICE_ID()+"&orderid="+appPreferences.getORDER_ID();
        final StringRequest stringRequest=new StringRequest(url, new Response.Listener<String>() {
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
                        double finalAmmount=Double.parseDouble(mBill.getBilldetails().getOrderTotal())- ammount;
                        mPayment.setPromocode(promoCode);
                        mPayment.setDiscountprice(""+ammount);
                        mPayment.setOrderprice(""+finalAmmount);
                        mPayment.setDiscountprice(""+ammount);
                        mPayment.setTotalprice(""+finalAmmount);
                    }
                    Util.createErrorAlert(View_Pay_BillActivity.this, "", status);
                    double amounts=Double.parseDouble(mBill.getBilldetails().getOrderTotal())- ammount;
                    mTvOrderTotal.setText("$ "+amounts);
                    mEtPromocodeMsg.setText(status);
                    mEtPromocodeMsg.setTextColor(Color.BLACK);
                    mTvDiscount.setText("$ "+ammount);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.createErrorAlert(View_Pay_BillActivity.this, "", getString(R.string.server_error));
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}