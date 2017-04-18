package com.effone.hostismeandroid.activity;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.activity.Search_ItemActivity;
import com.effone.hostismeandroid.adapter.AutocompleteCustomArrayAdapter;
import com.effone.hostismeandroid.model.MyObject;

/**
 * Created by sumanth.peddinti on 4/17/2017.
 */
public class CustomAutoCompleteTextChangedListener implements TextWatcher {

    public static final String TAG = "CustomAutoCompleteTextChangedListener";
    Context context;

    public CustomAutoCompleteTextChangedListener(Context context){
        this.context = context;
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence userInput, int start, int before, int count) {

        try{

            // if you want to see in the logcat what the user types
           // Log.e("", "User input: " + userInput);

            Search_ItemActivity search_itemActivity = ((Search_ItemActivity) context);

            // update the adapater
            search_itemActivity.myAdapter.notifyDataSetChanged();

            // get suggestions from the database
            MyObject[] myObjs = search_itemActivity.databaseH.read(userInput.toString());

            // update the adapter
            search_itemActivity.myAdapter = new AutocompleteCustomArrayAdapter(search_itemActivity, R.layout.list_view_row, myObjs);

            search_itemActivity.myAutoComplete.setAdapter(search_itemActivity.myAdapter);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}