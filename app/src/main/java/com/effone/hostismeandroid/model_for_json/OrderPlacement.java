package com.effone.hostismeandroid.model_for_json;

/**
 * Created by sumanth.peddinti on 6/21/2017.
 */

public class OrderPlacement {
    private Order order;

    public Order getOrder ()
    {
        return order;
    }

    public void setOrder (Order order)
    {
        this.order = order;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [order = "+order+"]";
    }
}
