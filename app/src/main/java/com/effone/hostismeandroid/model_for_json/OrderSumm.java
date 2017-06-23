package com.effone.hostismeandroid.model_for_json;

/**
 * Created by sarith.vasu on 22-06-2017.
 */

public class OrderSumm {
    private String orderts;

    private String orderstatus;

    private String totalprice;

    private String order_id;

    public String getOrderts ()
    {
        return orderts;
    }

    public void setOrderts (String orderts)
    {
        this.orderts = orderts;
    }

    public String getOrderstatus ()
    {
        return orderstatus;
    }

    public void setOrderstatus (String orderstatus)
    {
        this.orderstatus = orderstatus;
    }

    public String getTotalprice ()
    {
        return totalprice;
    }

    public void setTotalprice (String totalprice)
    {
        this.totalprice = totalprice;
    }

    public String getOrder_id ()
    {
        return order_id;
    }

    public void setOrder_id (String order_id)
    {
        this.order_id = order_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [orderts = "+orderts+", orderstatus = "+orderstatus+", totalprice = "+totalprice+", order_id = "+order_id+"]";
    }

}
