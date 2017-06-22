package com.effone.hostismeandroid.model_for_json;

import java.util.ArrayList;

/**
 * Created by sarith.vasu on 09-06-2017.
 */

public class Order {
    private String restaurant_id;

    public String getRestaurantId() {
        return this.restaurant_id;
    }

    public void setRestaurantId(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    private String phaseid;

    public String getPhaseid() {
        return this.phaseid;
    }

    public void setPhaseid(String phaseid) {
        this.phaseid = phaseid;
    }

    private String device_id;

    public String getDeviceId() {
        return this.device_id;
    }

    public void setDeviceId(String device_id) {
        this.device_id = device_id;
    }

    private String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private int tableno;

    public int getTableno() {
        return this.tableno;
    }

    public void setTableno(int tableno) {
        this.tableno = tableno;
    }

    private double orderprice;

    public double getOrderprice() {
        return this.orderprice;
    }

    public void setOrderprice(double orderprice) {
        this.orderprice = orderprice;
    }

    private double tax;

    public double getTax() {
        return this.tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    private double totalprice;

    public double getTotalprice() {
        return this.totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    private ArrayList<Menuitems> menuitems;

    public ArrayList<Menuitems> getMenuitems() {
        return this.menuitems;
    }

    public void setMenuitems(ArrayList<Menuitems> menuitems) {
        this.menuitems = menuitems;
    }
}
