package com.effone.hostismeandroid.model;

import java.util.ArrayList;

/**
 * Created by sarith.vasu on 10-05-2017.
 */

public class Bill {
    private String bill_no;
    private String bill_date;
    private String order_id;
    private Restaurant address;
    private ArrayList<Order_Items> orderItems;
    private ArrayList<TaxItems> taxItems;

    public Bill(Restaurant address, ArrayList<Order_Items> orderItems, ArrayList<TaxItems> taxItems) {
        this.address = address;
        this.orderItems = orderItems;
        this.taxItems = taxItems;
    }

    public Restaurant getAddress() {
        return address;
    }

    public void setAddress(Restaurant address) {
        this.address = address;
    }

    public ArrayList<Order_Items> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<Order_Items> orderItems) {
        this.orderItems = orderItems;
    }

    public ArrayList<TaxItems> getTaxItems() {
        return taxItems;
    }

    public void setTaxItems(ArrayList<TaxItems> taxItems) {
        this.taxItems = taxItems;
    }
    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getBill_date() {
        return bill_date;
    }

    public void setBill_date(String bill_date) {
        this.bill_date = bill_date;
    }
    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
