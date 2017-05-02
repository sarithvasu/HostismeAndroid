package com.effone.hostismeandroid.model;

import java.util.ArrayList;

/**
 * Created by sarith.vasu on 17-04-2017.
 */

public class HISMenuItem {
    private String name;
    private ArrayList<String> ingredint;
    private int qty;
    private float price;

    public boolean isAllowMultiple() {
        return allowMultiple;
    }

    public void setAllowMultiple(boolean allowMultiple) {
        this.allowMultiple = allowMultiple;
    }

    private boolean allowMultiple;

    public HISMenuItem(String name, ArrayList<String> ingredint, int qty, float price, boolean allowMultiple) {
        this.name = name;
        this.ingredint = ingredint;
        this.qty = qty;
        this.price = price;
        this.allowMultiple = allowMultiple;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getIngredint() {
        return ingredint;
    }

    public void setIngredint(ArrayList<String> ingredint) {
        this.ingredint = ingredint;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }



}
