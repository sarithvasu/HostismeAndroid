package com.effone.hostismeandroid.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.effone.hostismeandroid.MainActivity;
import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.common.AppPreferences;
import com.effone.hostismeandroid.common.Common;
import com.effone.hostismeandroid.model.Tables;
import com.effone.hostismeandroid.model_for_json.ServiceRequest;
import com.effone.hostismeandroid.model_for_json.ServiceRequestJson;
import com.effone.hostismeandroid.util.Util;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.effone.hostismeandroid.common.URL.REQUEST_SERVICE;


/**
 * Created by sumanth.peddinti on 4/12/2017.
 */

public class Book_a_tableActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner mSpTableNo;
    Button mBtViewMenu;
    private AppPreferences mAppPreferences;
    private Spinner mSpTableToNo;
    private TextView mMoveTableTitle, mTvTableNoForm,mTvSelectedTableNo;
    private LinearLayout mMoveTableLay;
    private RelativeLayout mBookTableLay;
    private ProgressDialog pDialog;
    private Tables mTables;
    private ServiceRequestJson mServiceRequestJson;
    private Gson mGson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_a_table);
        mAppPreferences = new AppPreferences(this);
        mGson = new Gson();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        if (mAppPreferences.getRESTAURANT_NAME() != "") {
            if (mAppPreferences.getTABLE_NAME() == 0 || mAppPreferences.getTABLE_NAME() == 9999) {
                Common.setCustomTitile(this, getString(R.string.book_a_table), mAppPreferences.getRESTAURANT_NAME());
            } else {
                Common.setCustomTitile(this, getString(R.string.move_a_table), mAppPreferences.getRESTAURANT_NAME());
            }

        }
        //init();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!Util.Operations.isOnline(this)) {
            Intent intent = new Intent(getApplicationContext(), NoNetworkActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else {
            init();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //init();
    }

    private void init() {
        mTvSelectedTableNo=(TextView)findViewById(R.id.tv_selected_table_no);
        mTvSelectedTableNo.setVisibility(View.GONE);
        mSpTableNo = (Spinner) findViewById(R.id.et_table_no);
        mSpTableToNo = (Spinner) findViewById(R.id.tv_table_no_to);
        mTvTableNoForm = (TextView) findViewById(R.id.tv_table_no_form);
        mMoveTableLay = (LinearLayout) findViewById(R.id.move_table_lay);
        mMoveTableTitle = (TextView) findViewById(R.id.move_table_title);
        mBookTableLay = (RelativeLayout) findViewById(R.id.book_table_lay);
        mBtViewMenu = (Button) findViewById(R.id.bt_subit);
        if (mAppPreferences.getTABLE_NAME() == 0 || mAppPreferences.getTABLE_NAME() == 9999) {
            if(mAppPreferences.getTABLE_NAME()==9999){
                mTvSelectedTableNo.setVisibility(View.VISIBLE);
                mTvSelectedTableNo.setText(getString(R.string.you_have_selected_to_go));
            }
            else {
                mTvSelectedTableNo.setVisibility(View.GONE);
            }
            mBookTableLay.setVisibility(View.VISIBLE);
            mMoveTableTitle.setVisibility(View.GONE);
            mMoveTableLay.setVisibility(View.GONE);
            //  mBtViewMenu.setText(getString(R.string.view_cart));
        } else {
            mTvSelectedTableNo.setVisibility(View.GONE);
            mBookTableLay.setVisibility(View.GONE);
            mMoveTableTitle.setVisibility(View.VISIBLE);
            mMoveTableLay.setVisibility(View.VISIBLE);
            if (mAppPreferences.getTABLE_NAME() == 8888)
                mTvTableNoForm.setText(getString(R.string.bar));
            else
                mTvTableNoForm.setText("" + mAppPreferences.getTABLE_NAME());
            mBtViewMenu.setText(getString(R.string.submit));
        }
        ArrayList<String> tableNos = new ArrayList<>();
        if (mAppPreferences.getTABLE_NAME() != 9999)
            tableNos.add(getString(R.string.togo));
        if (mAppPreferences.getTABLE_NAME() != 8888)
            tableNos.add(getString(R.string.bar));
        for (int i = 1; i <= mAppPreferences.getNUMBER_OF_TABLES(); i++) {
            if (mAppPreferences.getTABLE_NAME() != i)
                tableNos.add("" + i);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Book_a_tableActivity.this, android.R.layout.simple_spinner_item, tableNos);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        if (mAppPreferences.getTABLE_NAME() == 0|| mAppPreferences.getTABLE_NAME() == 9999) {
            mSpTableNo.setAdapter(dataAdapter);
        } else {
            mSpTableToNo.setAdapter(dataAdapter);
        }

        /*pDialog = new ProgressDialog(this);
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
                        hidePDialog();
//                        Util.createErrorAlert(Book_a_tableActivity.this, "", error.getMessage());
                    }
                });
        AppController.getInstance().addToRequestQueue(stringRequest);*/

        mBtViewMenu.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                final View v = findViewById(R.id.home_btn);

                if (v != null) {
                    v.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            return false;
                        }
                    });
                }
            }
        });
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
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_subit:
                String tableNo = "";
                boolean fromMoveTable = false;
                if (mAppPreferences.getTABLE_NAME() == 0|| mAppPreferences.getTABLE_NAME() == 9999) {
                    try {
                        tableNo = mSpTableNo.getSelectedItem().toString();
                    } catch (Exception e) {
                            Log.e("",""+e);

                    }
                } else {
                    try {
                        tableNo = mSpTableToNo.getSelectedItem().toString();
                        fromMoveTable = true;
                    } catch (Exception e) {


                    }
                }
                if (tableNo.length() >= 1) {


                    if (fromMoveTable) {
                        if (!mAppPreferences.getORDER_ID().equals("")){
                            if (!Util.Operations.isOnline(this)) {
                                Intent intent = new Intent(getApplicationContext(), NoNetworkActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                            else {
                                if (tableNo.equals(getString(R.string.togo))) {
                                    requestForaTable("" + 9999);
                                    Log.e("String", "" + 9999);
                                } else if (tableNo.equals(getString(R.string.bar))) {
                                    requestForaTable("" + 8888);
                                    Log.e("String", "" + 8888);
                                } else {
                                    requestForaTable("" + Integer.parseInt(tableNo));
                                }
                            }
                    }else{
                            if (tableNo.equals(getString(R.string.togo)))
                                mAppPreferences.setTABLE_NAME(9999);
                             else if (tableNo.equals(getString(R.string.bar)))
                                mAppPreferences.setTABLE_NAME(8888);
                             else
                                mAppPreferences.setTABLE_NAME(Integer.parseInt(tableNo));

                            Intent intent = new Intent(Book_a_tableActivity.this, MenuActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();


                        }


                    } else {
                        if (tableNo.equals(getString(R.string.togo))) {
                            mAppPreferences.setTABLE_NAME(9999);
                            Log.e("String", "" + 9999);
                        } else if (tableNo.equals(getString(R.string.bar))) {
                            mAppPreferences.setTABLE_NAME(8888);
                            Log.e("String", "" + 8888);
                        } else {
                            mAppPreferences.setTABLE_NAME(Integer.parseInt(tableNo));
                            Log.e("String", "" + 8888);
                        }

                        Intent intent = new Intent(Book_a_tableActivity.this, MenuActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }

                }
                break;
        }
    }

    private void requestForaTable(final String tableNo) {
        ServiceRequest ServiceRequest = new ServiceRequest();
        ServiceRequest.setComplaint("");
        ServiceRequest.setService_type("Move Table_" + mAppPreferences.getTABLE_NAME() + "_" + tableNo);
        ServiceRequest.setDevice_id(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));
        ServiceRequest.setOrder_id(mAppPreferences.getORDER_ID());
        ServiceRequest.setRestaurant_id(mAppPreferences.getRESTAURANT_ID());
        ServiceRequest.setTable_no("" + mAppPreferences.getTABLE_NAME());
        mServiceRequestJson = new ServiceRequestJson();
        mServiceRequestJson.setServiceRequest(ServiceRequest);
        final String json = mGson.toJson(mServiceRequestJson);
        StringRequest req = new StringRequest(Request.Method.POST, REQUEST_SERVICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String value = response;
                        if (!value.equals("")) {
                            try {
                                JSONObject jsonObjec = new JSONObject(response);
                                boolean status = jsonObjec.getBoolean("status");
                                if (status) {
                                    mAppPreferences.setTABLE_NAME(Integer.parseInt(tableNo));
                                    Intent intent = new Intent(Book_a_tableActivity.this, MenuActivity.class);
                                    startActivity(intent);
                                }
                            } catch (JSONException e) {

                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return json.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(req);
        /*place this in onRespons sucess*/

    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
