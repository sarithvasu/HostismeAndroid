package com.effone.hostismeandroid.model;

/**
 * Created by sumanth.peddinti on 4/17/2017.
 */

public class OrderSummary {
    private String id;

    private String Order_id;

    private Items_values[] items_values;

    private String datatime;

    private String status;

    private String restaurant_id;

    private String locationId;

    private String table_no;

    private String total_price;
    private  String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getOrder_id ()
    {
        return Order_id;
    }

    public void setOrder_id (String Order_id)
    {
        this.Order_id = Order_id;
    }

    public Items_values[] getItems_values ()
    {
        return items_values;
    }

    public void setItems_values (Items_values[] items_values)
    {
        this.items_values = items_values;
    }

    public String getDatatime ()
    {
        return datatime;
    }

    public void setDatatime (String datatime)
    {
        this.datatime = datatime;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getRestaurant_id ()
    {
        return restaurant_id;
    }

    public void setRestaurant_id (String restaurant_id)
    {
        this.restaurant_id = restaurant_id;
    }

    public String getLocationId ()
    {
        return locationId;
    }

    public void setLocationId (String locationId)
    {
        this.locationId = locationId;
    }

    public String getTable_no ()
    {
        return table_no;
    }

    public void setTable_no (String table_no)
    {
        this.table_no = table_no;
    }

    public String getTotal_price ()
    {
        return total_price;
    }

    public void setTotal_price (String total_price)
    {
        this.total_price = total_price;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", Order_id = "+Order_id+", items_values = "+items_values+", datatime = "+datatime+", status = "+status+", restaurant_id = "+restaurant_id+", locationId = "+locationId+", table_no = "+table_no+", total_price = "+total_price+"]";
    }
}
