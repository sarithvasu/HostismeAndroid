package com.effone.hostismeandroid.model;

/**
 * Created by sarith.vasu on 11-04-2017.
 */

public class Restaurant
{
    private String pincode;

    private String id;

    private String area;

    private String email;

    private String street;

    private String state;

    private String phno_2;

    private String no_of_tables;

    private String restaurant_name;

    private String phno_1;

    public String getPincode ()
    {
        return pincode;
    }

    public void setPincode (String pincode)
    {
        this.pincode = pincode;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getArea ()
    {
        return area;
    }

    public void setArea (String area)
    {
        this.area = area;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getStreet ()
    {
        return street;
    }

    public void setStreet (String street)
    {
        this.street = street;
    }

    public String getState ()
    {
        return state;
    }

    public void setState (String state)
    {
        this.state = state;
    }

    public String getPhno_2 ()
    {
        return phno_2;
    }

    public void setPhno_2 (String phno_2)
    {
        this.phno_2 = phno_2;
    }

    public String getNo_of_tables ()
    {
        return no_of_tables;
    }

    public void setNo_of_tables (String no_of_tables)
    {
        this.no_of_tables = no_of_tables;
    }

    public String getRestaurant_name ()
    {
        return restaurant_name;
    }

    public void setRestaurant_name (String restaurant_name)
    {
        this.restaurant_name = restaurant_name;
    }

    public String getPhno_1 ()
    {
        return phno_1;
    }

    public void setPhno_1 (String phno_1)
    {
        this.phno_1 = phno_1;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [pincode = "+pincode+", id = "+id+", area = "+area+", email = "+email+", street = "+street+", state = "+state+", phno_2 = "+phno_2+", no_of_tables = "+no_of_tables+", restaurant_name = "+restaurant_name+", phno_1 = "+phno_1+"]";
    }
}
