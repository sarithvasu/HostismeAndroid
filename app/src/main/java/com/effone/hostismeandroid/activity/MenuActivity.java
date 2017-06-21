package com.effone.hostismeandroid.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.effone.hostismeandroid.MainActivity;
import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.adapter.HISMenuPageAdapter;
import com.effone.hostismeandroid.common.AppPreferences;
import com.effone.hostismeandroid.common.OnDataChangeListener;
import com.effone.hostismeandroid.common.OnHandClickInterface;
import com.effone.hostismeandroid.common.UpdateableInterface;
import com.effone.hostismeandroid.db.SqlOperation;
import com.effone.hostismeandroid.model.CartItems;
import com.effone.hostismeandroid.model.Categories;
import com.effone.hostismeandroid.model.Content;
import com.effone.hostismeandroid.model.Items;
import com.effone.hostismeandroid.model.Sample;
import com.effone.hostismeandroid.model_for_menu.Dinner;
import com.effone.hostismeandroid.model_for_menu.ModelTotal;
import com.effone.hostismeandroid.util.Util;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.effone.hostismeandroid.common.URL.GET_MENU;
import static com.effone.hostismeandroid.common.URL.menu_url;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener, UpdateableInterface, OnDataChangeListener, OnHandClickInterface {

    public ArrayList<Content> mDinnerList =new ArrayList<>();
    public ArrayList<Content> mBreakfastList=new ArrayList<>();
    public ArrayList<Content> mLunchList=new ArrayList<>();
    public ArrayList<Content> mAlldayList=new ArrayList<>();

    public ArrayList<Items> m=new ArrayList<>();



    private RequestQueue mQueue;
    private Sample sample;
    private ViewPager mVpMainMenu;
    private AppPreferences mAppPreferences;

    private TextView mTvConfirm, mTvSummary;
    SqlOperation sqlOperation;
    ProgressDialog pDialog;
    ArrayList<Items> itemss;
    HashMap<String, Items[]> pagerItem = new LinkedHashMap<>();
    Categories categories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_menu);
        mAppPreferences = new AppPreferences(this);
        itemss = new ArrayList<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setToolbar();
        mVpMainMenu = (ViewPager) findViewById(R.id.vp_main_menu);
        mVpMainMenu.setCurrentItem(1, true);
        mTvConfirm = (TextView) findViewById(R.id.tv_confirm);
        mTvSummary = (TextView) findViewById(R.id.tv_summary_details);
        mTvConfirm.setOnClickListener(this);


        mQueue = Volley.newRequestQueue(this);
        showOrderItems();

        final StringRequest stringRequest = new StringRequest(GET_MENU+"1",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String[] keysValues = new String[]{};
                        pDialog = new ProgressDialog(MenuActivity.this);
                        // Showing progress dialog before making http request
                        pDialog.setMessage("Loading...");
                        pDialog.show();

                        try {
                            JSONObject jsonObjec = new JSONObject(response);
                            JSONObject menuJsonObject = jsonObjec.getJSONObject("MenuList");
                            Iterator<String> foodTimes = menuJsonObject.keys();


                            while (foodTimes.hasNext()) {
                                String foodTime = foodTimes.next();

                                JSONObject jsonObject = menuJsonObject.getJSONObject(foodTime);// her i will get the cousin(Italin,..etc) of muenu list
                                Iterator<String> countryCousins = jsonObject.keys();
                               ArrayList<Items> itemses=new ArrayList();
                                while (countryCousins.hasNext()) {
                                    String countryCousin = countryCousins.next();
                                    JSONArray jsonArray = jsonObject.getJSONArray(countryCousin);
                                    Content[] contents = new Content[jsonArray.length()];
                                    Items items=new Items();
                                    items.setName(countryCousin);
                                    if (!countryCousin.equals("Beverages")) {
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject contentJson = jsonArray.getJSONObject(i);
                                            Content content = new Content();
                                            content.setMenu_item_id(Integer.parseInt(contentJson.getString("id")));
                                            content.setName(contentJson.getString("item"));
                                            content.setMenu_types(contentJson.getString("menu_type"));
                                            content.setCountryCusine(countryCousin);
                                            content.setPrice(Float.parseFloat(contentJson.getString("price")));
                                            content.setIngredients(contentJson.getString("description"));
                                            contents[i] = content;
                                        }
                                        items.setContent(contents);
                                        itemses.add(items);
                                    }
                                    else{

                                    }
                                }
                                Items[] item1=new Items[0];
                                pagerItem.put(foodTime, itemses.toArray(item1));

                            }
                            //doFilterMitheCountryCusine(menuJsonObject);

                        } catch (JSONException e) {
                            Log.e("", "" + e);
                        }
                        HISMenuPageAdapter menuPageAdapter = new HISMenuPageAdapter(getSupportFragmentManager(), pagerItem);
                        mVpMainMenu.setAdapter(menuPageAdapter);
                        hidePDialog();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hidePDialog();
                        if (!(MenuActivity.this.isFinishing())) {
                            Util.createErrorAlert(MenuActivity.this, "", error.getMessage());
                        }
                    }
                });
        mQueue.add(stringRequest);

    }

    private void doFilterMitheCountryCusine( JSONObject menuJsonObject) {
        ArrayList<Content> italian=new ArrayList<>();
        ArrayList<Content> mexican=new ArrayList<>();
        ArrayList<Content> asian=new ArrayList<>();
        ArrayList<Content> others=new ArrayList<>();
        Iterator<String> countryCousins = menuJsonObject.keys();
        ArrayList<Items>  dinnerItems=new ArrayList<>();
        ArrayList<Items>  moringItems=new ArrayList<>();
        ArrayList<Items>  lunchItems=new ArrayList<>();


        while (countryCousins.hasNext()) {

            for (Content co :mDinnerList) {
                if(co.getCountryCusine().equals("Italian")){
                    italian.add(co);
                }
                if(co.getCountryCusine().equals("Mexican")){
                    mexican.add(co);
                }
                if(co.getCountryCusine().equals("Asian")){
                    asian.add(co);
                }
                if(co.getCountryCusine().equals("Others")){
                    others.add(co);
                }
            }
            for (Content co :mLunchList) {
                if(co.getCountryCusine().equals("Italian")){
                    italian.add(co);
                }
                if(co.getCountryCusine().equals("Mexican")){
                    mexican.add(co);
                }
                if(co.getCountryCusine().equals("Asian")){
                    asian.add(co);
                }
                if(co.getCountryCusine().equals("Others")){
                    others.add(co);
                }
            }
            for (Content co :mBreakfastList) {
                if(co.getCountryCusine().equals("Italian")){
                    italian.add(co);
                }
                if(co.getCountryCusine().equals("Mexican")){
                    mexican.add(co);
                }
                if(co.getCountryCusine().equals("Asian")){
                    asian.add(co);
                }
                if(co.getCountryCusine().equals("Others")){
                    others.add(co);
                }
            }
            for (Content co :mLunchList) {
                if(co.getCountryCusine().equals("Italian")){
                    italian.add(co);
                }
                if(co.getCountryCusine().equals("Mexican")){
                    mexican.add(co);
                }
                if(co.getCountryCusine().equals("Asian")){
                    asian.add(co);
                }
                if(co.getCountryCusine().equals("Others")){
                    others.add(co);
                }
            }
            for (Content co :mAlldayList) {
                if(co.getCountryCusine().equals("Italian")){
                    italian.add(co);
                }
                if(co.getCountryCusine().equals("Mexican")){
                    mexican.add(co);
                }
                if(co.getCountryCusine().equals("Asian")){
                    asian.add(co);
                }
                if(co.getCountryCusine().equals("Others")){
                    others.add(co);
                }
            }





            }


    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!Util.Operations.isOnline(this)) {
            Util.createNetErrorDialog(this);
        } else {
            showOrderItems();
        }
    }

    private void setToolbar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        LinearLayout customView = (LinearLayout)
                LayoutInflater.from(this).inflate(R.layout.default_custom_title,
                        null);
        ActionBar.LayoutParams params = new
                ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        getSupportActionBar().setCustomView(customView, params);
        TextView cust_ttile = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.tv_custom_titile);
        cust_ttile.setText(getString(R.string.title_menu));
        ;
        TextView cust_sub_ttile = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.tv_custom_sub_titile);
        cust_sub_ttile.setText(mAppPreferences.getRESTAURANT_NAME());
        ImageView iv_back = (ImageView) getSupportActionBar().getCustomView().findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                sqlOperation = new SqlOperation(this);
                sqlOperation.open();
                if (sqlOperation.getItemCountInCart() != 0) {
                    //we are deleteing the item from the cart based on the item_id and ItemName
                    Intent intent = new Intent(this, PlaceOrderActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MenuActivity.this, "Pick the items Count is .0", Toast.LENGTH_SHORT).show();

                }

                break;
            default:
                break;
        }
    }

    ArrayList<CartItems> cartItemses;

    private void showOrderItems() {
        sqlOperation = new SqlOperation(this);
        sqlOperation.open();
        cartItemses = sqlOperation.getCartItems(1);
        int totalCount = 0;
        int totalPrice = 0;
        sqlOperation.close();
        for (int i = 0; i < cartItemses.size(); i++) {
            totalCount += cartItemses.get(i).getItemQuantity();
            totalPrice += cartItemses.get(i).getItemPrice() * cartItemses.get(i).getItemQuantity();

        }
        mTvSummary.setText(totalCount + " Items in Cart \n " + totalPrice + " Plus charges");
    }

    @Override
    public void update() {
        showOrderItems();
    }


    @Override
    public void onDataChanged(int size) {
        showOrderItems();
    }

    @Override
    public void getFragmentPosition(int postion) {
        mVpMainMenu.setCurrentItem(mVpMainMenu.getCurrentItem() + 1, true);
    }
}