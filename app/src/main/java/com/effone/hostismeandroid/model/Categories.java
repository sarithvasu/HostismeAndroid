package com.effone.hostismeandroid.model;

/**
 * Created by sumanth.peddinti on 5/10/2017.
 */


public class Categories
{
    private Items[] Items;

    private String name;

    public Items[] getItems ()
    {
        return Items;
    }

    public void setItems (Items[] Items)
    {
        this.Items = Items;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Items = "+Items+", name = "+name+"]";
    }
}


