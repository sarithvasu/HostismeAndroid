package com.effone.hostismeandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.effone.hostismeandroid.MainActivity;
import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.common.Common;
import com.effone.hostismeandroid.util.Util;

public class NoNetworkActivity extends AppCompatActivity {
    private Button mBtSubit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Util.Operations.isOnline(this)) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else {
            setContentView(R.layout.activity_no_network);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayUseLogoEnabled(false);
            Common.setCustomTitileCOnfirmation(this,getString(R.string.no_internet_message), null);
            mBtSubit=(Button)findViewById(R.id.bt_subit);
            mBtSubit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Util.Operations.isOnline(NoNetworkActivity.this)) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAffinity(this);
    }
}
