package com.effone.hostismeandroid.model;

/**
 * Created by sumanth.peddinti on 5/11/2017.
 */

public class CartItems {
    public String ItemCatagerie;
    public String ItemSubCat;
    public int ItemMenuCatId;
    public String ItemName;
    public String ItemIngred;
    public float ItemPrice;
    public int ItemQuantity;
    public String MenuType;

    public String getSpecial() {
        return Special;
    }

    public void setSpecial(String special) {
        Special = special;
    }

    public String Special;




    public String getMenuType() {
        return MenuType;
    }

    public void setMenuType(String menuType) {
        MenuType = menuType;
    }

    public String getItemCatagerie() {
        return ItemCatagerie;
    }

    public void setItemCatagerie(String itemCatagerie) {
        ItemCatagerie = itemCatagerie;
    }

    public String getItemSubCat() {
        return ItemSubCat;
    }

    public void setItemSubCat(String itemSubCat) {
        ItemSubCat = itemSubCat;
    }

    public int getItemMenuCatId() {
        return ItemMenuCatId;
    }

    public void setItemMenuCatId(int itemMenuCatId) {
        ItemMenuCatId = itemMenuCatId;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemIngred() {
        return ItemIngred;
    }

    public void setItemIngred(String itemIngred) {
        ItemIngred = itemIngred;
    }

    public float getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(float itemPrice) {
        ItemPrice = itemPrice;
    }

    public int getItemQuantity() {
        return ItemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        ItemQuantity = itemQuantity;
    }
}
