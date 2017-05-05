package com.effone.hostismeandroid.model;

/**
 * Created by sarith.vasu on 11-04-2017.
 */

public class Restaurant {

    private int id;
    private String name;
    private String address;
    private String city;
    private String country;


    public Restaurant(int id, String name, String address, String city, String country) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
    }



    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getRestName() {
        return name;
    }

    public void setRestName(String name) {
        this.name = name;
    }

    public String getRestAdress() {
        return address;
    }

    public void setRestAdress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", address = "+address+", name = "+name+", country = "+country+", city = "+city+"]";
    }

}
