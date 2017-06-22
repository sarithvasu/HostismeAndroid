package com.effone.hostismeandroid.model_for_confirmation;

/**
 * Created by sumanth.peddinti on 6/21/2017.
 */

public class RootObject {
    private boolean status;

    public boolean getStatus() { return this.status; }

    public void setStatus(boolean status) { this.status = status; }

    private OrderConfirmation orderConfirmation;

    public OrderConfirmation getOrderConfirmation() { return this.orderConfirmation; }

    public void setOrderConfirmation(OrderConfirmation orderConfirmation) { this.orderConfirmation = orderConfirmation; }
}
