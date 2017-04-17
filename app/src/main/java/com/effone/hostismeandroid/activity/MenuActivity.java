package com.effone.hostismeandroid.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.common.AppPreferences;

public class MenuActivity extends AppCompatActivity {
    AppPreferences appPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appPreferences=new AppPreferences(this);
        if(appPreferences.getREST_NAME()!= null)
        {

        }else {
            toolbar.setTitle(""+appPreferences.getREST_NAME());

        }
    }

}
