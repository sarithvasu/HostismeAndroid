package com.effone.hostismeandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.effone.hostismeandroid.db.SqlOperations;
import com.effone.hostismeandroid.model.Order_Items;
import com.effone.hostismeandroid.model.OrderedItemSummary;
import com.effone.hostismeandroid.model.TaxItems;


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

    private EditText mEtPromoCodeNumber;
    private Button mBtApply;
    private TextView mTvBillSummary,mTvRestAddress,mTvBillDate,mTvBillNo,mTvOrderId,mTvOrderTotal;
    ArrayList<Order_Items> order_itemses;
    private  TextView mTvRestName;
    private  SqlOperations sqliteoperation;
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

    private void init() {

        mLvItemQuantity=(ListView)findViewById(R.id.lv_items_list);
        mLvTaxQuality=(ListView)findViewById(R.id.lv_tax_menu);
        mTvSubmit=(TextView)findViewById(R.id.tv_submit);
        mTvSubmit.setOnClickListener(this);

        taxItemses = new ArrayList<TaxItems>();
        order_itemses=new ArrayList<Order_Items>();
        order_itemses= getOrderHistory();
        mTvOrderId.setText(": "+order_id);
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        mTvBillDate.setText(": "+currentDateTimeString);
        mTvBillNo.setText(": "+tsLong);
        //adding oreder_items:
       /* Order_Items order_items=new Order_Items("Olives Warm Marinated",10,10);
        Order_Items order_items1=new Order_Items("Bread Fresh Baked",10,9);
        order_itemses.add(order_items);
        order_itemses.add(order_items1);
*/

        orderItemDetails=new OrderItemDetailsAdapter(this,R.layout.order_summary_items,order_itemses);
        mLvItemQuantity.setAdapter(orderItemDetails);


        TaxItems res1 = new TaxItems("Total before Tax", totalbyOrder);
        TaxItems res2 = new TaxItems("Service Charges", serviceTax);
        TaxItems res3 = new TaxItems("Service Tax",vatTax);
        TaxItems res4 = new TaxItems("VAT Tax", ser);
        taxItemses.add(res1);
        taxItemses.add(res2);
        taxItemses.add(res3);
        taxItemses.add(res4);
        double sum=totalbyOrder+serviceTax+vatTax+ser;
        mTvOrderTotal.setText("$ "+sum);
        taxDetailsAdapter=new TaxDetailsAdapter(this,R.layout.tax_items,taxItemses);
        mLvTaxQuality.setAdapter(taxDetailsAdapter);
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
            order_id=map.get("order_id");
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
            for(int i=0;i<order_ids.size();i++) {
                sqliteoperation.updatePlaceOrderStatus(order_ids.get(i));
            }
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
            promocodeServerSending();
            mEtPromocodeMsg.setText("Promo code  available");
    }

    }

    private void promocodeServerSending() {

    }

}