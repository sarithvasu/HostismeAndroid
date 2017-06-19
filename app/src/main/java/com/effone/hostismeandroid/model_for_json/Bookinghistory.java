package com.effone.hostismeandroid.model_for_json;

/**
 * Created by sarith.vasu on 16-06-2017.
 */

public class Bookinghistory {
    private String status;

    private String description;

    private String table_no;

    private String bill_amount;

    private String bill_no;

    private String order_id;

    private String date;

    private String restaurant_name;

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getTable_no ()
    {
        return table_no;
    }

    public void setTable_no (String table_no)
    {
        this.table_no = table_no;
    }

    public String getBill_amount ()
    {
        return bill_amount;
    }

    public void setBill_amount (String bill_amount)
    {
        this.bill_amount = bill_amount;
    }

    public String getBill_no ()
    {
        return bill_no;
    }

    public void setBill_no (String bill_no)
    {
        this.bill_no = bill_no;
    }

    public String getOrder_id ()
    {
        return order_id;
    }

    public void setOrder_id (String order_id)
    {
        this.order_id = order_id;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public String getRestaurant_name ()
    {
        return restaurant_name;
    }

    public void setRestaurant_name (String restaurant_name)
    {
        this.restaurant_name = restaurant_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status = "+status+", description = "+description+", table_no = "+table_no+", bill_amount = "+bill_amount+", bill_no = "+bill_no+", order_id = "+order_id+", date = "+date+", restaurant_name = "+restaurant_name+"]";
    }
}
