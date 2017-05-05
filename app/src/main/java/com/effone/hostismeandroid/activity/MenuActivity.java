package com.effone.hostismeandroid.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.adapter.MenuExpandableListViewAdapter;
import com.effone.hostismeandroid.common.AppPreferences;
import com.effone.hostismeandroid.model.HISMenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    AppPreferences appPreferences;
    private ExpandableListView mExpandableMenu;
    HashMap<String, List<HISMenuItem>> mHashMap;
    private MenuExpandableListViewAdapter mMenuExpandableListViewAdapter;
    private TextView mTvConfirm,mTvSumaryDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Menu");
        mExpandableMenu=(ExpandableListView)findViewById(R.id.elv_menu);
        init();
        setItems();
        setToolbar();
        appPreferences=new AppPreferences(this);
        if(appPreferences.getREST_NAME()!= null)
        {

        }else {
            toolbar.setTitle(""+appPreferences.getREST_NAME());

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
        TextView cust_ttile=(TextView)getSupportActionBar().getCustomView().findViewById(R.id.tv_custom_titile);
        cust_ttile.setText(getString(R.string.title_menu));
        TextView cust_sub_ttile=(TextView)getSupportActionBar().getCustomView().findViewById(R.id.tv_custom_sub_titile);
        cust_sub_ttile.setText("Restaurant Name One");
        ImageView iv_back=(ImageView) getSupportActionBar().getCustomView().findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void init() {
        mTvConfirm=(TextView)findViewById(R.id.tv_confirm);
     mTvSumaryDetails=(TextView)findViewById(R.id.tv_summary_details);

   /*     mTvSumaryDetails.getDr*/
        mTvConfirm.setOnClickListener(this);

    }

    private  void setItems(){

        // Array list for header
        ArrayList<String> header = new ArrayList<String>();

        // Array list for child items
        List<HISMenuItem> child1 = new ArrayList<HISMenuItem>();
        List<HISMenuItem> child2 = new ArrayList<HISMenuItem>();
        List<HISMenuItem> child3 = new ArrayList<HISMenuItem>();


        // Hash map for both header and child
       mHashMap = new HashMap<String, List<HISMenuItem>>();

        // Adding headers to list
      //  for (int i = 1; i < 5; i++) {
            header.add("Starters");
            header.add("Mains");
            header.add("Desserts");

       // }
        // Adding child data
        for (int i = 1; i < 3; i++) {
            child1.add(new HISMenuItem("Bread Fresh Baked Sourdough",null,0,45.00f,true));
        }
        // Adding child data
        for (int i = 1; i < 3; i++) {
            child2.add(new HISMenuItem("Roast Chiken Ballantine",null,0,90.00f,false));
        }
        // Adding child data
        for (int i = 1; i < 3; i++) {
            child3.add(new HISMenuItem("White Chocalate & Nougat Semifreddo",null,0,70.00f,false));
        }
        // Adding child data


        // Adding header and childs to hash map
        mHashMap.put(header.get(0), child1);
        mHashMap.put(header.get(1), child2);
        mHashMap.put(header.get(2), child3);
        mMenuExpandableListViewAdapter = new MenuExpandableListViewAdapter(this, header, mHashMap);
        mExpandableMenu.setAdapter(mMenuExpandableListViewAdapter);
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
    public void onClick(View view) {
      /*  HashMap<String, List<HISMenuItem>> hashMap = new HashMap<String, List<HISMenuItem>>();
        ArrayList<HISMenuItem> hisMenuItems;
        for (int i = 0; i < mMenuExpandableListViewAdapter .getGroupCount(); i++){
           hisMenuItems= new ArrayList<HISMenuItem>();
            for(int j=0;j<mMenuExpandableListViewAdapter.getChildrenCount(i);j++){
                hisMenuItems.add((HISMenuItem)mMenuExpandableListViewAdapter.getChild(j,i));
            }
            hashMap .put((String) mMenuExpandableListViewAdapter .getGroup(i),hisMenuItems);
    }
        Iterator it = hashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            int s=((List<HISMenuItem>) pair.getValue()).get(0).getQty();
            System.out.println("JNJNKNKNKNKNK"+pair.getKey() + " = " );
            it.remove(); // avoids a ConcurrentModificationException
        }*/
        Intent intent=new Intent(this,ViewCartActivity.class);
        startActivity(intent);
    }
}
