package com.effone.hostismeandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.effone.hostismeandroid.R;

/**
 * Created by sumanth.peddinti on 4/12/2017.
 */

public class Book_a_tableActivity extends AppCompatActivity implements View.OnClickListener {
        EditText mEtTableNo;
    Button mBtViewMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_a_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.book_a_table));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        init();
    }

    private void init() {
        mEtTableNo=(EditText)findViewById(R.id.et_table_no);
        mBtViewMenu=(Button)findViewById(R.id.bt_subit);
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
                String tableNo=mEtTableNo.getText().toString();
                if(tableNo.length() >= 2){
                    Toast.makeText(this,"done",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(this,MenuActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(this,"Enter the table no",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
