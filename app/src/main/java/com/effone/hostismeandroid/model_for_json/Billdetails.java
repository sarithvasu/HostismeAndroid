
package com.effone.hostismeandroid.model_for_json;


public class Billdetails {

    private String orderTotal;

    private String billno;

    private String bill_date;

    private String merchantid;

    public String getOrderTotal ()
    {
        return orderTotal;
    }

    public void setOrderTotal (String orderTotal)
    {
        this.orderTotal = orderTotal;
    }

    public String getBillno ()
    {
        return billno;
    }

    public void setBillno (String billno)
    {
        this.billno = billno;
    }

    public String getBill_date ()
    {
        return bill_date;
    }

    public void setBill_date (String bill_date)
    {
        this.bill_date = bill_date;
    }

    public String getMerchantid ()
    {
        return merchantid;
    }

    public void setMerchantid (String merchantid)
    {
        this.merchantid = merchantid;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [orderTotal = "+orderTotal+", billno = "+billno+", bill_date = "+bill_date+", merchantid = "+merchantid+"]";
    }
}
