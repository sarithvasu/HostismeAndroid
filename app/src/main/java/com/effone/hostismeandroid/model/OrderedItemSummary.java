package com.effone.hostismeandroid.model;

/**
 * Created by sarith.vasu on 17-04-2017.
 */

public class OrderedItemSummary {
    private  int item_cat;
    private  int item_food;
    private String itemName;
    private int quatity;
    private float price;
    private double serviceCharges;
    private double vat;

    public int getItem_cat() {
        return item_cat;
    }

    public void setItem_cat(int item_cat) {
        this.item_cat = item_cat;
    }

    public int getItem_food() {
        return item_food;
    }

    public void setItem_food(int item_food) {
        this.item_food = item_food;
    }

    public void setServiceCharges(double serviceCharges) {
        this.serviceCharges = serviceCharges;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public void setServiceTax(double serviceTax) {
        this.serviceTax = serviceTax;
    }

    private double serviceTax;
    private  float totalCost;

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public OrderedItemSummary(int  item_cat, int item_food, String itemName, float totalCost, int quatity, float price, double serviceCharges, double vat, double serviceTax) {
        this.item_cat=item_cat;
        this.item_food=item_food;
        this.itemName = itemName;
        this.totalCost=totalCost;

        this.quatity = quatity;
        this.price = price;
        this.serviceCharges = serviceCharges;
        this.vat = vat;
        this.serviceTax = serviceTax;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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

    public double getServiceCharges() {
        return serviceCharges;
    }

    public void setServiceCharges(float serviceCharges) {
        this.serviceCharges = serviceCharges;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(float vat) {
        this.vat = vat;
    }

    public double getServiceTax() {
        return serviceTax;
    }

    public void setServiceTax(float serviceTax) {
        this.serviceTax = serviceTax;
    }
}
