package com.effone.hostismeandroid.model;

/**
 * Created by sarith.vasu on 17-04-2017.
 */

public class OrderedItemSummary {
    private String ItemName;
    private int quatity;
    private float price;
    private float serviceCharges;
    private float vat;
    private float serviceTax;

    public OrderedItemSummary(String itemName, int quatity, float price, float serviceCharges, float vat, float serviceTax) {
        ItemName = itemName;
        this.quatity = quatity;
        this.price = price;
        this.serviceCharges = serviceCharges;
        this.vat = vat;
        this.serviceTax = serviceTax;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public int getQuatity() {
        return quatity;
    }

    public void setQuatity(int quatity) {
        this.quatity = quatity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getServiceCharges() {
        return serviceCharges;
    }

    public void setServiceCharges(float serviceCharges) {
        this.serviceCharges = serviceCharges;
    }

    public float getVat() {
        return vat;
    }

    public void setVat(float vat) {
        this.vat = vat;
    }

    public float getServiceTax() {
        return serviceTax;
    }

    public void setServiceTax(float serviceTax) {
        this.serviceTax = serviceTax;
    }
}
