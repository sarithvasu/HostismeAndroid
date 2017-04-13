package com.effone.hostismeandroid.model;

/**
 * Created by sarith.vasu on 11-04-2017.
 */

public class Restaurant {
    private String restName;
    private String restAdress;
    private String city;

    public Restaurant(String restName, String restAdress, String city, String country) {
        this.restName = restName;
        this.restAdress = restAdress;
        this.city = city;
        this.country = country;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public String getRestAdress() {
        return restAdress;
    }

    public void setRestAdress(String restAdress) {
        this.restAdress = restAdress;
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

    private String country;
}
