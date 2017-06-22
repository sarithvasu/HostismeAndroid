package com.effone.hostismeandroid.model_for_confirmation;

/**
 * Created by sumanth.peddinti on 6/21/2017.
 */

public class OrderItem {
    private String name;

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    private String unit_price;

    public String getUnitPrice() { return this.unit_price; }

    public void setUnitPrice(String unit_price) { this.unit_price = unit_price; }

    private String quantity;

    public String getQuantity() { return this.quantity; }

    public void setQuantity(String quantity) { this.quantity = quantity; }

    private String item_total_price;

    public String getItemTotalPrice() { return this.item_total_price; }

    public void setItemTotalPrice(String item_total_price) { this.item_total_price = item_total_price; }
}
