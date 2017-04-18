package com.effone.hostismeandroid.activity;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.effone.hostismeandroid.R;

public class Service_RequestActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEtTableToNo,mTvComplaintList;
    private TextView mTvSubmit;
    private RadioGroup mRadioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service__request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Service Request");
        // getSupportActionBar().setTitle(getString(R.string.booking_history));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        init();
    }

    private void init() {
        mEtTableToNo=(EditText)findViewById(R.id.tv_table_no_to);
        mTvComplaintList=(EditText)findViewById(R.id.et_complaints);
        mTvSubmit=(TextView)findViewById(R.id.tv_submit);
        mRadioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        mTvSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String tableNo=mEtTableToNo.getText().toString().trim();
        String complaint=mTvComplaintList.getText().toString().trim();
        int selectedId = mRadioGroup.getCheckedRadioButtonId();
        if(selectedId != -1){
            if(complaint.length()>200){
                if(tableNo.length()<2){
                    Toast.makeText(this, "Request has Sent", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "enter table no", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "enter Complaint Type", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this, "select one service Type", Toast.LENGTH_SHORT).show();
        }
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
