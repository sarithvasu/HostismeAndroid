package com.effone.hostismeandroid.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.app.AppController;
import com.effone.hostismeandroid.common.AppPreferences;
import com.effone.hostismeandroid.common.Common;
import com.effone.hostismeandroid.model.Bill;
import com.effone.hostismeandroid.model.Tables;
import com.effone.hostismeandroid.model.TaxItems;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.effone.hostismeandroid.common.URL.bill_url;
import static com.effone.hostismeandroid.common.URL.tables_url;


/**
 * Created by sumanth.peddinti on 4/12/2017.
 */

public class Book_a_tableActivity extends AppCompatActivity implements View.OnClickListener {
        private Spinner mSpTableNo;
    Button mBtViewMenu;
    private AppPreferences mAppPreferences;
    private Spinner mSpTableToNo;
    private TextView mMoveTableTitle,mTvTableNoForm;
    private LinearLayout mMoveTableLay;
    private RelativeLayout mBookTableLay;
    private ProgressDialog pDialog;
    private Tables mTables;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_a_table);
        mAppPreferences=new AppPreferences(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if(mAppPreferences.getRESTAURANT_NAME()!= "") {
            if(mAppPreferences.getTABLE_NAME()==0) {
                Common.setCustomTitile(this, getString(R.string.book_a_table), mAppPreferences.getRESTAURANT_NAME());
            }
            else{
                Common.setCustomTitile(this, getString(R.string.move_a_table), mAppPreferences.getRESTAURANT_NAME());
            }

        }
            init();

    }



    private void init() {
        mSpTableNo=(Spinner) findViewById(R.id.et_table_no);
        mSpTableToNo=(Spinner) findViewById(R.id.tv_table_no_to);
        mTvTableNoForm=(TextView) findViewById(R.id.tv_table_no_form);
        mMoveTableLay=(LinearLayout)findViewById(R.id.move_table_lay);
        mMoveTableTitle=(TextView)findViewById(R.id.move_table_title);
        mBookTableLay=(RelativeLayout)findViewById(R.id.book_table_lay);
        mBtViewMenu=(Button)findViewById(R.id.bt_subit);
        if(mAppPreferences.getTABLE_NAME()==0){
            mBookTableLay.setVisibility(View.VISIBLE);
            mMoveTableTitle.setVisibility(View.GONE);
            mMoveTableLay.setVisibility(View.GONE);
            mBtViewMenu.setText(getString(R.string.view_cart));
        }
        else{
            mBookTableLay.setVisibility(View.GONE);
            mMoveTableTitle.setVisibility(View.VISIBLE);
            mMoveTableLay.setVisibility(View.VISIBLE);
            mTvTableNoForm.setText(""+mAppPreferences.getTABLE_NAME());
            mBtViewMenu.setText(getString(R.string.submit));
        }
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest stringRequest = new StringRequest(tables_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hidePDialog();
                        Gson gson=new Gson();
                        mTables = gson.fromJson(response, Tables.class);
                        ArrayList<Integer> tableNos=mTables.getTable_nos();

                        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(Book_a_tableActivity.this, android.R.layout.simple_spinner_item, tableNos);

                        // Drop down layout style - list view with radio button
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // attaching data adapter to spinner
                        if(mAppPreferences.getTABLE_NAME()==0) {
                            mSpTableNo.setAdapter(dataAdapter);
                        }
                        else{
                            mSpTableToNo.setAdapter(dataAdapter);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Book_a_tableActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
        AppController.getInstance().addToRequestQueue(stringRequest);

        mBtViewMenu.setOnClickListener(this);

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
        switch(view.getId()){
            case R.id.bt_subit:
                String tableNo="";
                if(mAppPreferences.getTABLE_NAME()==0) {
                     tableNo = mSpTableNo.getSelectedItem().toString();
                }
                else{
                    tableNo = mSpTableToNo.getSelectedItem().toString();
                }
                if(tableNo.length() >= 2){
                    if(mAppPreferences.getTABLE_NAME()==0) {
                        mAppPreferences.setTABLE_NAME(Integer.parseInt(tableNo));
                        //     Toast.makeText(this,"done",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, MenuActivity.class);
                        startActivity(intent);
                    }
                }else{
                    Toast.makeText(this,"Enter the table no",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
