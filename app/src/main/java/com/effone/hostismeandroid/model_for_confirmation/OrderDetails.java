package com.effone.hostismeandroid.model_for_confirmation;

import com.effone.hostismeandroid.model.TaxItems;

import java.util.ArrayList;

/**
 * Created by sumanth.peddinti on 6/21/2017.
 */

public class OrderDetails {
    private ArrayList<OrderItem> orderItems;

    private ArrayList<TaxItems> taxItemses;

    public ArrayList<OrderItem> getOrderItems() { return this.orderItems; }

    public void setOrderItems(ArrayList<OrderItem> orderItems) { this.orderItems = orderItems; }
    public ArrayList<TaxItems> getTaxItems() { return this.taxItemses; }

    public void setTaxItems(ArrayList<TaxItems> taxItemses) { this.taxItemses = taxItemses; }

}
