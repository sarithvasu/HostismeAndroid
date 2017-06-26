package com.effone.hostismeandroid.model;

/**
 * Created by sumanth.peddinti on 5/10/2017.
 */

public class Content {
    private String ingredients;

    private int menu_item_id;



    private String countryCusine;

    private String menu_types;

    private float price;

    private String name;
    private String is_special;

    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getIs_special ()
    {
        return is_special;
    }

    public void setIs_special (String is_special)
    {
        this.is_special = is_special;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public int getMenu_item_id() {
        return menu_item_id;
    }

    public void setMenu_item_id(int menu_item_id) {
        this.menu_item_id = menu_item_id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public String getMenu_types() {
        return menu_types;
    }

    public void setMenu_types(String menu_types) {
        this.menu_types = menu_types;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCusine() {
        return countryCusine;
    }

    public void setCountryCusine(String countryCusine) {
        this.countryCusine = countryCusine;
    }

    @Override
    public String toString() {
        return "ClassPojo [ingredients = " + ingredients + ", menu_item_id = " + menu_item_id + ", price = " + price + ", name = " + name + "]";
    }


}
