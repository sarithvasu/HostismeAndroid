package com.effone.hostismeandroid.model;

/**
 * Created by sumanth.peddinti on 5/10/2017.
 */

public class Content {
    private String ingredients;

    private int menu_item_id;

    private float price;

    private String name;

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

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ClassPojo [ingredients = " + ingredients + ", menu_item_id = " + menu_item_id + ", price = " + price + ", name = " + name + "]";
    }
}
