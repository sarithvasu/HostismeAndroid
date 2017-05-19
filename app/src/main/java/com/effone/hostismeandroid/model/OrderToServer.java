package com.effone.hostismeandroid.model;

import java.util.ArrayList;

/**
 * Created by sumanth.peddinti on 5/12/2017.
 */

public class OrderToServer {
    private int locationId;
    private int restaurant_id;
    private int table_no;
    private String order_id;
    private double total_price;
    private ArrayList<OrderingMenu> Items;

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public int getTable_no() {
        return table_no;
    }

    public void setTable_no(int table_no) {
        this.table_no = table_no;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public ArrayList<OrderingMenu> getItems() {
        return Items;
    }

    public void setItems(ArrayList<OrderingMenu> items) {
        Items = items;
    }
}
