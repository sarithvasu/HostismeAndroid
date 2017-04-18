package com.effone.hostismeandroid.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.adapter.MenuItemSummeryListAdapter;

public class ViewCartActivity extends AppCompatActivity {
    private ListView mItemSummeryList;
    private MenuItemSummeryListAdapter mMenuItemSummeryListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        mItemSummeryList=(ListView)findViewById(R.id.item_summery_list);
        init();
    }

    private void init() {
    }

}
