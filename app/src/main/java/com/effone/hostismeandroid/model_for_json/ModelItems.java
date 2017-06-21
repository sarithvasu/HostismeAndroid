package com.effone.hostismeandroid.model_for_json;

/**
 * Created by sarith.vasu on 20-06-2017.
 */

public class ModelItems {
    private String id;

    private String price;

    private String menu_type;

    private String description;

    private String item;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getPrice ()
    {
        return price;
    }

    public void setPrice (String price)
    {
        this.price = price;
    }

    public String getMenu_type ()
    {
        return menu_type;
    }

    public void setMenu_type (String menu_type)
    {
        this.menu_type = menu_type;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getItem ()
    {
        return item;
    }

    public void setItem (String item)
    {
        this.item = item;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", price = "+price+", menu_type = "+menu_type+", description = "+description+", item = "+item+"]";
    }
}
