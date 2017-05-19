package com.effone.hostismeandroid.model;

/**
 * Created by sumanth.peddinti on 5/15/2017.
 */

public class Items_values {
    private String qunatity;

    private String item_id;

    public String getQunatity ()
    {
        return qunatity;
    }

    public void setQunatity (String qunatity)
    {
        this.qunatity = qunatity;
    }

    public String getItem_id ()
    {
        return item_id;
    }

    public void setItem_id (String item_id)
    {
        this.item_id = item_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [qunatity = "+qunatity+", item_id = "+item_id+"]";
    }
}
