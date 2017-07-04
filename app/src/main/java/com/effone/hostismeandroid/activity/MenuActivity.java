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
import com.effone.hostismeandroid.model_for_json.MenuTaxItens;
import com.effone.hostismeandroid.util.Util;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import static com.effone.hostismeandroid.common.URL.GET_MENU;

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
    LinkedHashMap<String, Items[]> pagerItem = new LinkedHashMap<>();
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

       // gettingDataFromService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mVpMainMenu!=null)
        mVpMainMenu.setAdapter(null);
        if (!Util.Operations.isOnline(this))
            Util.createNetErrorDialog(this);
        else {
            gettingDataFromService();
            showOrderItems();
        }
    }

    private void gettingDataFromService() {
        pagerItem = new LinkedHashMap<>();
        pDialog = new ProgressDialog(MenuActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        final StringRequest stringRequest = new StringRequest(GET_MENU+mAppPreferences.getRESTAURANT_ID(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String[] keysValues = new String[]{};

                        // Showing progress dialog before making http request



                        try {
                            JSONObject jsonObjec = new JSONObject(response);
                            JSONObject menuJsonObject = jsonObjec.getJSONObject("MenuList");
                            JSONArray taxName=jsonObjec.getJSONArray("Taxes");
                            gettingTaxValues(taxName);
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
                                    if (!countryCousin.equals("Beverages")) {
                                        if(jsonArray!=null && jsonArray.length()>0) {
                                            Items items = new Items();
                                            items.setName(countryCousin);
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                JSONObject contentJson = jsonArray.getJSONObject(i);
                                                Content content = new Content();
                                                content.setMenu_item_id(Integer.parseInt(contentJson.getString("id")));
                                                content.setName(contentJson.getString("item").trim());
                                                content.setMenu_types(contentJson.getString("menu_type").trim());
                                                content.setCountryCusine(countryCousin);
                                                content.setPrice(Float.parseFloat(contentJson.getString("price")));
                                                content.setIngredients(contentJson.getString("description").trim());
                                                content.setIs_special(contentJson.getString("is_special"));
                                                content.setQuantity(0);
                                                contents[i] = content;
                                            }
                                            items.setContent(contents);
                                            itemses.add(items);
                                        }
                                    }
                                    else {
                                        if (jsonArray != null && jsonArray.length() > 0) {
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                JSONObject contentJson = jsonArray.getJSONObject(i);
                                                Iterator<String> bevTypes = contentJson.keys();

                                                while (bevTypes.hasNext()) {
                                                    String bevType = bevTypes.next();

                                                    JSONArray jsonbevType = contentJson.getJSONArray(bevType);
                                                    Log.e("", "" + jsonArray);
                                                    if(jsonbevType!=null&&jsonbevType.length()>0) {
                                                        Items items = new Items();
                                                        items.setName(countryCousin + "-" + bevType);
                                                        Content[] contentBevlist = new Content[jsonbevType.length()];
                                                        for (int j = 0; j < jsonbevType.length(); j++) {
                                                            JSONObject contentBev = jsonbevType.getJSONObject(j);
                                                            Content content = new Content();
                                                            content.setMenu_item_id(Integer.parseInt(contentBev.getString("id")));
                                                            content.setName(contentBev.getString("item").trim());
                                                            content.setMenu_types("");
                                                    /*content.setCountryCusine(countryCousin);*/
                                                            content.setQuantity(0);
                                                            content.setPrice(Float.parseFloat(contentBev.getString("price")));
                                                            content.setIngredients(contentBev.getString("description").trim());
                                                            content.setIs_special("3");
                                                            contentBevlist[j] = content;
                                                        }
                                                        items.setContent(contentBevlist);
                                                        itemses.add(items);
                                                    }
                                                }

                                            }

                                        }
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
                        menuPageAdapter.notifyDataSetChanged();
                        mVpMainMenu.setAdapter(menuPageAdapter);



                        hidePDialog();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hidePDialog();
                        if (!(MenuActivity.this.isFinishing())) {
                            Util.createErrorAlert(MenuActivity.this, "", "Server Error");
                        }
                        Intent intent=new Intent(MenuActivity.this,MenuActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        mQueue.add(stringRequest);

    }
    public  static List<MenuTaxItens> taxList;
    private void gettingTaxValues(JSONArray os) {
        taxList= new ArrayList<>();
        MenuTaxItens[] menuTaxItems;
        Gson gson=new Gson();
        menuTaxItems=gson.fromJson(String.valueOf(os),MenuTaxItens[].class);
        taxList  = new ArrayList<MenuTaxItens>(Arrays.asList(menuTaxItems));
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
                    Util.createOKAlert(MenuActivity.this,getString(R.string.Alert),getString(R.string.please_select_one_item));

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
        mTvSummary.setText(totalCount + ""+getString(R.string.item_cart)+" \n " + totalPrice + " "+getString(R.string.plus_taxes));

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