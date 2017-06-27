package com.effone.hostismeandroid.model_for_json;

/**
 * Created by sarith.vasu on 26-06-2017.
 */

public class Payment
{
    private String promocode;

    private String orderprice;

    private String orderstatus;

    private String tax;

    private String phaseid;

    private String totalprice;

    private String tableno;

    private String restaurant_id;

    private String device_id;

    private String discountprice;

    private String order_id;

    public String getPromocode ()
    {
        return promocode;
    }

    public void setPromocode (String promocode)
    {
        this.promocode = promocode;
    }

    public String getOrderprice ()
    {
        return orderprice;
    }

    public void setOrderprice (String orderprice)
    {
        this.orderprice = orderprice;
    }

    public String getOrderstatus ()
    {
        return orderstatus;
    }

    public void setOrderstatus (String orderstatus)
    {
        this.orderstatus = orderstatus;
    }

    public String getTax ()
    {
        return tax;
    }

    public void setTax (String tax)
    {
        this.tax = tax;
    }

    public String getPhaseid ()
    {
        return phaseid;
    }

    public void setPhaseid (String phaseid)
    {
        this.phaseid = phaseid;
    }

    public String getTotalprice ()
    {
        return totalprice;
    }

    public void setTotalprice (String totalprice)
    {
        this.totalprice = totalprice;
    }

    public String getTableno ()
    {
        return tableno;
    }

    public void setTableno (String tableno)
    {
        this.tableno = tableno;
    }

    public String getRestaurant_id ()
    {
        return restaurant_id;
    }

    public void setRestaurant_id (String restaurant_id)
    {
        this.restaurant_id = restaurant_id;
    }

    public String getDevice_id ()
    {
        return device_id;
    }

    public void setDevice_id (String device_id)
    {
        this.device_id = device_id;
    }

    public String getDiscountprice ()
    {
        return discountprice;
    }

    public void setDiscountprice (String discountprice)
    {
        this.discountprice = discountprice;
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
        return "ClassPojo [promocode = "+promocode+", orderprice = "+orderprice+", orderstatus = "+orderstatus+", tax = "+tax+", phaseid = "+phaseid+", totalprice = "+totalprice+", tableno = "+tableno+", restaurant_id = "+restaurant_id+", device_id = "+device_id+", discountprice = "+discountprice+", order_id = "+order_id+"]";
    }
}
