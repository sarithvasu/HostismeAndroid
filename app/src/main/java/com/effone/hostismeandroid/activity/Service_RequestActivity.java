package com.effone.hostismeandroid.activity;

import android.content.Intent;
import android.provider.MediaStore;
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

import com.effone.hostismeandroid.MainActivity;
import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.common.Common;

public class Service_RequestActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mTvComplaintList;
    private TextView mTvSubmit;
    private RadioGroup mRadioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service__request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
       // String tableNo=mEtTableToNo.getText().toString().trim();
        String complaint=mTvComplaintList.getText().toString().trim();
        int selectedId = mRadioGroup.getCheckedRadioButtonId();
        if(selectedId != -1){
            if(complaint.length()>20){
                // send request
                Toast.makeText(this, "No web api", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "enter Complaint Type", Toast.LENGTH_SHORT).show();

            }

        }else {
            Toast.makeText(this, "select one service Type", Toast.LENGTH_SHORT).show();
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
