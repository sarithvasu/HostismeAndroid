package com.effone.hostismeandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.adapter.MenuItemSummeryListAdapter;
import com.effone.hostismeandroid.common.AppPreferences;
import com.effone.hostismeandroid.model.OrderedItemSummary;

import java.util.ArrayList;

public class ViewCartActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView mItemSummeryList;
    private ArrayList<OrderedItemSummary> orderedItemSummaries;
    private MenuItemSummeryListAdapter mMenuItemSummeryListAdapter;
    private TextView mTvPlaceOrder;
    private EditText mEtTableNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setCustomTitile();
        mItemSummeryList=(ListView)findViewById(R.id.item_summery_list);
        setValue();
        init();
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
        mTvPlaceOrder=(TextView)findViewById(R.id.tv_place_order);
        mEtTableNo=(EditText)findViewById(R.id.et_table_no) ;
        mTvPlaceOrder.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.tv_place_order) {
            if(mEtTableNo.getText().toString().trim().length()> 3) {
                Intent intent = new Intent(this, ConfirmationActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this,"enter the Table.no",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
