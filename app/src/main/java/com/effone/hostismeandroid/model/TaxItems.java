package com.effone.hostismeandroid.model;

/**
 * Created by sumanth.peddinti on 4/17/2017.
 */

public class TaxItems {
    private String name;
    private int  value;

    public TaxItems(String name, int price) {
        this.name=name;
        this.value=price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


}
