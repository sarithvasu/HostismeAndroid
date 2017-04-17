package com.effone.hostismeandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.effone.hostismeandroid.R;

/**
 * Created by sumanth.peddinti on 4/12/2017.
 */

public class Booking_History extends AppCompatActivity {
  TextView mTvSelectedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_history_activity);
        mTvSelectedDate=(TextView)findViewById(R.id.Date);
        mTvSelectedDate.setVisibility(View.GONE);
        getSupportActionBar().setTitle(getString(R.string.booking_history));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
