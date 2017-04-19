package com.effone.hostismeandroid.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.adapter.AutocompleteCustomArrayAdapter;
import com.effone.hostismeandroid.changeListener.CustomAutoCompleteView;
import com.effone.hostismeandroid.database.DatabaseHandler;
import com.effone.hostismeandroid.model.MyObject;

import java.util.ArrayList;

public class Search_ItemActivity extends AppCompatActivity {
    private ListView lv;
    CustomAutoCompleteView myAutoComplete;

    // adapter for auto-complete
    ArrayAdapter<MyObject> myAdapter;

    // for database operations
    DatabaseHandler databaseH;
    // Listview Adapter
    ArrayAdapter<String> adapter;

    ArrayList<String> listView;

    // Search EditText
    EditText inputSearch;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search City.");
        // getSupportActionBar().setTitle(getString(R.string.booking_history));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        databaseH = new DatabaseHandler(this);
        listView=new ArrayList<>();
        mListView=(ListView)findViewById(R.id.list_view);
        adding();
        try {

            // put sample data to database
            insertSampleData();

            // autocompletetextview is in activity_main.xml
            myAutoComplete = (CustomAutoCompleteView) findViewById(R.id.myautocomplete);

            myAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {

                    RelativeLayout rl = (RelativeLayout) arg1;
                    TextView tv = (TextView) rl.getChildAt(0);
                    myAutoComplete.setText(tv.getText().toString());

                }

            });

            // add the listener so it will tries to suggest while the user types
            myAutoComplete.addTextChangedListener(new CustomAutoCompleteTextChangedListener(Search_ItemActivity.this));

            // ObjectItemData has no value at first
            MyObject[] ObjectItemData = new MyObject[0];

            // set the custom ArrayAdapter
            myAdapter = new AutocompleteCustomArrayAdapter(this, R.layout.list_item, ObjectItemData);
            myAutoComplete.setAdapter(myAdapter);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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
    private void adding() {
        listView.add( "January");
        listView.add( "February" );
        listView.add( "March");
        listView.add( "April" );
        listView.add( "May");
        listView.add("June");
        listView.add( "July" );
        listView.add("August");
        listView.add( "September" );
        listView.add("October" );
        listView.add( "November" );
        listView.add( "December" );
        listView.add( "New Caledonia this is just to make and see if the text will go down");
        listView.add( "New Zealand this is just to make and see if the text will go down");
        listView.add( "Papua New Guinea this is just to make and see if the text will go down");
        listView.add( "COFFEE-1K");
        listView.add( "coffee raw");
        listView.add( "authentic COFFEE");
        listView.add( "k12-coffee");
        listView.add( "view coffee");
        listView.add( "Indian-coffee-two");
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(Search_ItemActivity.this,android.R.layout.simple_list_item_1,listView);
        mListView.setAdapter(arrayAdapter);
    }


    public void insertSampleData(){

        // CREATE
        databaseH.create( new MyObject("January") );
        databaseH.create( new MyObject("February") );
        databaseH.create( new MyObject("March") );
        databaseH.create( new MyObject("April") );
        databaseH.create( new MyObject("May") );
        databaseH.create( new MyObject("June") );
        databaseH.create( new MyObject("July") );
        databaseH.create( new MyObject("August") );
        databaseH.create( new MyObject("September") );
        databaseH.create( new MyObject("October") );
        databaseH.create( new MyObject("November") );
        databaseH.create( new MyObject("December") );
        databaseH.create( new MyObject("New Caledonia this is just to make and see if the text will go down") );
        databaseH.create( new MyObject("New Zealand this is just to make and see if the text will go down") );
        databaseH.create( new MyObject("Papua New Guinea this is just to make and see if the text will go down") );
        databaseH.create( new MyObject("COFFEE-1K") );
        databaseH.create( new MyObject("coffee raw") );
        databaseH.create( new MyObject("authentic COFFEE") );
        databaseH.create( new MyObject("k12-coffee") );
        databaseH.create( new MyObject("view coffee") );
        databaseH.create( new MyObject("Indian-coffee-two") );

    }
}
