package com.effone.hostismeandroid.model_for_json;

/**
 * Created by sarith.vasu on 26-06-2017.
 */

public class PaymentJson
{
    private Payment Payment;

    public Payment getPayment ()
    {
        return Payment;
    }

    public void setPayment (Payment Payment)
    {
        this.Payment = Payment;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Payment = "+Payment+"]";
    }
}

