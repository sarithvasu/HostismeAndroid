package com.effone.hostismeandroid.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.adapter.MenuExpandableListViewAdapter;
import com.effone.hostismeandroid.common.AppPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    AppPreferences appPreferences;
    private ExpandableListView mExpandableMenu;
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
        appPreferences=new AppPreferences(this);
        if(appPreferences.getREST_NAME()!= null)
        {

        }else {
            toolbar.setTitle(""+appPreferences.getREST_NAME());

        }
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
        List<String> child1 = new ArrayList<String>();
        List<String> child2 = new ArrayList<String>();
        List<String> child3 = new ArrayList<String>();


        // Hash map for both header and child
        HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();

        // Adding headers to list
      //  for (int i = 1; i < 5; i++) {
            header.add("Starters");
            header.add("Mains");
            header.add("Disserts");
       // }
        // Adding child data
        for (int i = 1; i < 3; i++) {
            child1.add("Bread Fresh Baked Sourdough");
        }
        // Adding child data
        for (int i = 1; i < 3; i++) {
            child2.add("Roast Chiken Ballantine");
        }
        // Adding child data
        for (int i = 1; i < 3; i++) {
            child3.add("White Chocalate & Nougat Semifreddo");
        }
        // Adding child data


        // Adding header and childs to hash map
        hashMap.put(header.get(0), child1);
        hashMap.put(header.get(1), child2);
        hashMap.put(header.get(2), child3);
        mMenuExpandableListViewAdapter = new MenuExpandableListViewAdapter(this, header, hashMap);
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
        Intent intent=new Intent(this,ViewCartActivity.class);
        startActivity(intent);
    }
}
