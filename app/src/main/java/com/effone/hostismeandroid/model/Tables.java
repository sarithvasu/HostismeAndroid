package com.effone.hostismeandroid.model;

import java.util.ArrayList;

/**
 * Created by sarith.vasu on 19-05-2017.
 */

public class Tables {
    private String location_id;
    private String restaurant_id;
    private String order_id;
    ArrayList<Integer> table_nos;

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public ArrayList<Integer> getTable_nos() {
        return table_nos;
    }

    public void setTable_nos(ArrayList<Integer> table_nos) {
        this.table_nos = table_nos;
    }
}
