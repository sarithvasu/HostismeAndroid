package com.effone.hostismeandroid.model_for_confirmation;

/**
 * Created by sumanth.peddinti on 6/21/2017.
 */

public class OrderSummary {
    private int order_id;

    public int getOrderId() { return this.order_id; }

    public void setOrderId(int order_id) { this.order_id = order_id; }

    private String orderts;

    public String getOrderts() { return this.orderts; }

    public void setOrderts(String orderts) { this.orderts = orderts; }

    private int totalprice;

    public int getTotalprice() { return this.totalprice; }

    public void setTotalprice(int totalprice) { this.totalprice = totalprice; }

    private String orderstatus;

    public String getOrderstatus() { return this.orderstatus; }

    public void setOrderstatus(String orderstatus) { this.orderstatus = orderstatus; }
}