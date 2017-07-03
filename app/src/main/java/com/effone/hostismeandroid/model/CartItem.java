package com.effone.hostismeandroid.model;

import android.content.Context;

import org.json.JSONArray;

/**
 * Created by sumanth.peddinti on 5/3/2017.
 */

public class CartItem {
    private float totalPrice;
    private int itemCount;
    private JSONArray jsonArray;
    private  Context context;


    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }
}
