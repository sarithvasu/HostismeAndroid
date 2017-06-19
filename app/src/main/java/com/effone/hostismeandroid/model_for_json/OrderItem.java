
package com.effone.hostismeandroid.model_for_json;


public class OrderItem {

    private String name;

    private String quantity;

    private String item_total_price;

    private String unit_price;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getQuantity ()
    {
        return quantity;
    }

    public void setQuantity (String quantity)
    {
        this.quantity = quantity;
    }

    public String getItem_total_price ()
    {
        return item_total_price;
    }

    public void setItem_total_price (String item_total_price)
    {
        this.item_total_price = item_total_price;
    }

    public String getUnit_price ()
    {
        return unit_price;
    }

    public void setUnit_price (String unit_price)
    {
        this.unit_price = unit_price;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+", quantity = "+quantity+", item_total_price = "+item_total_price+", unit_price = "+unit_price+"]";
    }
}
