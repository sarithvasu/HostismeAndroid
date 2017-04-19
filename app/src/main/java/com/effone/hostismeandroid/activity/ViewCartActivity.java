package com.effone.hostismeandroid.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.adapter.MenuItemSummeryListAdapter;
import com.effone.hostismeandroid.common.AppPreferences;
import com.effone.hostismeandroid.model.OrderedItemSummary;

import java.util.ArrayList;

public class ViewCartActivity extends AppCompatActivity {
    private ListView mItemSummeryList;
    private ArrayList<OrderedItemSummary> orderedItemSummaries;
    private MenuItemSummeryListAdapter mMenuItemSummeryListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.view_cart));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mItemSummeryList=(ListView)findViewById(R.id.item_summery_list);
        setValue();
        init();
    }

    private void init() {
    }
    private void setValue() {
        orderedItemSummaries= new ArrayList<OrderedItemSummary>();
        OrderedItemSummary orderedItemSummary=new OrderedItemSummary("Olive Warm Marinated",10,100.00f,2.0f,1.0f,4.0f);
        OrderedItemSummary orderedItemSummary1=new OrderedItemSummary("Bread Fresh Baked",10,120.00f,2.7f,1.6f,4.5f);
        OrderedItemSummary orderedItemSummary2=new OrderedItemSummary("Bread Fresh Baked Marinated",10,120.00f,2.7f,1.6f,4.5f);
        orderedItemSummaries.add(orderedItemSummary);
        orderedItemSummaries.add(orderedItemSummary1);
        orderedItemSummaries.add(orderedItemSummary2);
        mMenuItemSummeryListAdapter=new MenuItemSummeryListAdapter(this,R.layout.summery_list_item,orderedItemSummaries);
        mItemSummeryList.setAdapter(mMenuItemSummeryListAdapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
