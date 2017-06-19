package com.effone.hostismeandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.effone.hostismeandroid.MainActivity;
import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.adapter.MenuItemSummeryListAdapter;
import com.effone.hostismeandroid.common.AppPreferences;
import com.effone.hostismeandroid.common.OnDataChangeListener;
import com.effone.hostismeandroid.db.SqlOperations;
import com.effone.hostismeandroid.model.CartItem;
import com.effone.hostismeandroid.model.OrderedItemSummary;
import com.effone.hostismeandroid.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ViewCartActivity extends AppCompatActivity implements View.OnClickListener,OnDataChangeListener {
    private ListView mItemSummeryList;
    private ArrayList<OrderedItemSummary> orderedItemSummaries;
    private MenuItemSummeryListAdapter mMenuItemSummeryListAdapter;
    private TextView mTvPlaceOrder;
    private EditText mEtTableNo;
    private   JSONObject jObj;
    private TextView mTvItemPrice,mTvItemCount,mTvChargers,mTvEstimatedTotal;
    private  CartItem cartItem;
    private AppPreferences appPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        Intent intent=getIntent();

        appPreferences=new AppPreferences(this);
        cartItem=new CartItem();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setCustomTitile();
        mItemSummeryList=(ListView)findViewById(R.id.item_summery_list);
        init();
        setValue();

    }
    private void setCustomTitile() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        LinearLayout customView = (LinearLayout)
                LayoutInflater.from(this).inflate(R.layout.default_custom_title,
                        null);
        ActionBar.LayoutParams params = new
                ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        getSupportActionBar().setCustomView(customView, params);
        TextView cust_ttile=(TextView)getSupportActionBar().getCustomView().findViewById(R.id.tv_custom_titile);
        cust_ttile.setText(getString(R.string.view_cart));
        TextView cust_sub_ttile=(TextView)getSupportActionBar().getCustomView().findViewById(R.id.tv_custom_sub_titile);
        cust_sub_ttile.setVisibility(View.GONE);
        ImageView iv_back=(ImageView) getSupportActionBar().getCustomView().findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        mTvItemCount=(TextView)findViewById(R.id.tv_items);
        mTvItemPrice=(TextView)findViewById(R.id.tv_items_price);
        mTvChargers=(TextView)findViewById(R.id.tv_charger_price);
        mTvEstimatedTotal=(TextView)findViewById(R.id.tv_estimated_price);

        mTvPlaceOrder=(TextView)findViewById(R.id.tv_place_order);
        mEtTableNo=(EditText)findViewById(R.id.et_table_no) ;
        if(appPreferences.getTABLE_NAME() !=  0){
            mEtTableNo.setText(""+appPreferences.getTABLE_NAME());
            mEtTableNo.setFocusable(false);
        }
        mTvPlaceOrder.setOnClickListener(this);
        setValuesInto();
    }

    private void setValuesInto() {
        getDataFromDatabase();
        try {
            mTvItemPrice.setText(""+totalbyOrder);
            mTvItemCount.setText("Items ("+ Math.round(totalNumberOfItems)+")");
            mTvChargers.setText(""+taxAmountCalculation());
            double sum=totalbyOrder+taxAmountCalculation();
            mTvEstimatedTotal.setText(""+sum);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private SqlOperations sqliteoperation;
    float totalbyOrder = 0;
    float totalNumberOfItems = 0;
    String item_cata;
    public static double serviceTax=06.00;
    public static double vatTax=12.00;  public static double ser=05.00;

    public double taxAmountCalculation()
    {
        return serviceTax+vatTax+ser;
    }
    private void getDataFromDatabase() {
        orderedItemSummaries= new ArrayList<OrderedItemSummary>();
        sqliteoperation = new SqlOperations(getApplicationContext());
        sqliteoperation.open();
        List<HashMap<String, String>> dictionary = sqliteoperation.getOrder("SUMANTH");
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
            item_cat= Integer.parseInt(map.get("index_category"));
            item_food= Integer.parseInt(map.get("index_food"));
            totalbyFood = map.get("totalByFood");
            price = map.get("price");
            quantity = map.get("quantity");
            foodName = map.get("food_name");
            messageOrder += "\n " + j + " - " + foodName + " (" + price + " $  x  " + quantity + ")  " + totalbyFood + "$";
            totalbyOrder += Float.parseFloat(totalbyFood);
            totalNumberOfItems += Float.parseFloat(quantity);
            item_cata=map.get("index_food");
            OrderedItemSummary orderedItemSummary=new OrderedItemSummary(item_cat,item_food,foodName,Float.parseFloat(totalbyFood),Math.round(Float.parseFloat(quantity)),Float.parseFloat(price),serviceTax,vatTax,ser);
            orderedItemSummaries.add(orderedItemSummary);
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

    private void setValue() {
       // mMenuItemSummeryListAdapter=new MenuItemSummeryListAdapter(this,R.layout.summery_list_item,orderedItemSummaries);
      //  mItemSummeryList.setAdapter(mMenuItemSummeryListAdapter);
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


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.tv_place_order) {
            if(mEtTableNo.getText().toString().trim().length()> 2) {
                Long tsLong = System.currentTimeMillis()/1000;
                String ts = tsLong.toString();
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                sqliteoperation = new SqlOperations(getApplicationContext());
                sqliteoperation.open();
                int table_no = Integer.parseInt(mEtTableNo.getText().toString().trim());
                   if(sqliteoperation.updateOrderId(mEtTableNo.getText().toString().trim()) != 0) {

                       appPreferences.setTABLE_NAME(table_no);
                       sqliteoperation.placeOrder(mEtTableNo.getText().toString().trim(), table_no, appPreferences.getRESTAURANT_NAME(), currentDateTimeString, "BOOKED", item_cata, totalbyOrder, Math.round(totalNumberOfItems));
                   }
                sqliteoperation.close();

                Intent intent = new Intent(this, ConfirmationActivity.class);
                intent.putExtra("Order_id",mEtTableNo.getText().toString().trim());
                startActivity(intent);
            }else{
                Util.createErrorAlert(ViewCartActivity.this, "", "enter the Table.no");
            }
        }
    }

    @Override
    public void onDataChanged(int size) {
     totalbyOrder = 0;
        totalNumberOfItems = 0;
        setValuesInto();
        setValue();
        /*try {
            mTvItemPrice.setText(""+totalbyOrder);
            mTvItemCount.setText("Items ("+ Math.round(totalNumberOfItems)+")");
            mTvChargers.setText(""+taxAmountCalculation()*Math.round(totalNumberOfItems));
            double sum=totalbyOrder+taxAmountCalculation()*Math.round(totalNumberOfItems);
            mTvEstimatedTotal.setText(""+sum);
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
    }
}
