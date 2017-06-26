package com.effone.hostismeandroid.activity;

import android.content.Intent;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

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
import com.effone.hostismeandroid.model_for_json.ServiceRequest;
import com.effone.hostismeandroid.model_for_json.ServiceRequestJson;
import com.effone.hostismeandroid.util.Util;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.effone.hostismeandroid.common.URL.POST_ORDER;
import static com.effone.hostismeandroid.common.URL.REQUEST_SERVICE;

public class Service_RequestActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mTvComplaintList;
    private TextView mTvSubmit;
    private RadioGroup mRadioGroup;
    private AppPreferences mAppPreferences;
    private Gson mGson;
    private ServiceRequestJson mServiceRequestJson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service__request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAppPreferences=new AppPreferences(this);
        mGson=new Gson();

        // getSupportActionBar().setTitle(getString(R.string.booking_history));
        Common.setCustomTitile(this,"Request Service",null);

        init();
    }

    private void init() {

        mTvComplaintList=(EditText)findViewById(R.id.et_complaints);
        mTvSubmit=(TextView)findViewById(R.id.tv_submit);
        mRadioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        mTvSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_submit) {

                if (mRadioGroup.getCheckedRadioButtonId() == -1) {

                    Util.createOKAlert(Service_RequestActivity.this,"Alert","Please Select Service Type.");
                } else {
                    // get selected radio button from radioGroup
                    int selectedId = mRadioGroup.getCheckedRadioButtonId();
                    if (selectedId != -1) {
                        sendingDataServer();
                    }
// find the radiobutton by returned id



            }
        }
    }

    private void sendingDataServer() {
        if(mAppPreferences.getTABLE_NAME()!=0) {
            ServiceRequest ServiceRequest = new ServiceRequest();
            ServiceRequest.setComplaint(mTvComplaintList.getText().toString());
            int selectedId = mRadioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) findViewById(selectedId);
            ServiceRequest.setService_type(radioButton.getText().toString());
            ServiceRequest.setDevice_id(mAppPreferences.getDEVICE_ID());
            ServiceRequest.setOrder_id("1"+mAppPreferences.getORDER_ID());
            ServiceRequest.setRestaurant_id(mAppPreferences.getRESTAURANT_ID());
            ServiceRequest.setTable_no("" + mAppPreferences.getTABLE_NAME());
            mServiceRequestJson=new ServiceRequestJson();
            mServiceRequestJson.setServiceRequest(ServiceRequest);
            final String json=mGson.toJson(mServiceRequestJson);
            StringRequest req = new StringRequest(Request.Method.POST, REQUEST_SERVICE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String value = response;
                            if (!value.equals("")) {
                                try {
                                    JSONObject jsonObjec = new JSONObject(response);
                                    boolean status =jsonObjec.getBoolean("status");
                                    if(status) {
                                        Intent intent = new Intent(Service_RequestActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }
                                catch (JSONException e){

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
        }
        else{
            Util.createErrorAlert(Service_RequestActivity.this, "", "PLEASE SELECT TABLE NUMBER SELECTED");
        }
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

}
