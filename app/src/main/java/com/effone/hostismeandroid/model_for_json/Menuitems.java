package com.effone.hostismeandroid.model_for_json;

/**
 * Created by sarith.vasu on 09-06-2017.
 */

public class Menuitems
{
    private String id;

    private String itemtype;

    private String quantity;

    private String menuType;


    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public Menuitems(int itemMenuCatId, int itemQuantity, String itemType, String menuType) {
        this.id=""+itemMenuCatId;
        this.quantity=""+itemQuantity;
        this.itemtype=itemType;
        this.menuType=menuType;

    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getItemtype ()
    {
        return itemtype;
    }

    public void setItemtype (String itemtype)
    {
        this.itemtype = itemtype;
    }

    public String getQuantity ()
    {
        return quantity;
    }

    public void setQuantity (String quantity)
    {
        this.quantity = quantity;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", itemtype = "+itemtype+", quantity = "+quantity+"]";
    }
}