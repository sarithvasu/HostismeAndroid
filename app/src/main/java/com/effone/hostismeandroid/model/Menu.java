package com.effone.hostismeandroid.model;

/**
 * Created by sumanth.peddinti on 5/10/2017.
 */


public class Menu
{
    private String restaurant_id;

    private Categories[] categories;

    private String location_id;

    public String getRestaurant_id ()
    {
        return restaurant_id;
    }

    public void setRestaurant_id (String restaurant_id)
    {
        this.restaurant_id = restaurant_id;
    }

    public Categories[] getCategories ()
    {
        return categories;
    }

    public void setCategories (Categories[] categories)
    {
        this.categories = categories;
    }

    public String getLocation_id ()
    {
        return location_id;
    }

    public void setLocation_id (String location_id)
    {
        this.location_id = location_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [restaurant_id = "+restaurant_id+", categories = "+categories+", location_id = "+location_id+"]";
    }
}