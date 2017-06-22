package com.effone.hostismeandroid.model_for_confirmation;

/**
 * Created by sumanth.peddinti on 6/21/2017.
 */

public class OrderConfirmation {
    private OrderSummary order_summary;

    public OrderSummary getOrderSummary() { return this.order_summary; }

    public void setOrderSummary(OrderSummary order_summary) { this.order_summary = order_summary; }

    private OrderDetails order_details;

    public OrderDetails getOrderDetails() { return this.order_details; }

    public void setOrderDetails(OrderDetails order_details) { this.order_details = order_details; }
}
