
package com.effone.hostismeandroid.model_for_json;

import java.util.List;

public class Bill {

    private Billdetails billdetails;
    private List<OrderItem> orderItems = null;
    private List<TaxItem> taxItems = null;

    public Billdetails getBilldetails() {
        return billdetails;
    }

    public void setBilldetails(Billdetails billdetails) {
        this.billdetails = billdetails;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<TaxItem> getTaxItems() {
        return taxItems;
    }

    public void setTaxItems(List<TaxItem> taxItems) {
        this.taxItems = taxItems;
    }

}
