package com.effone.hostismeandroid.model;

/**
 * Created by sumanth.peddinti on 5/12/2017.
 */

public class OrderingMenu {
    private int menu_item_id;
    private int quantity;

    public OrderingMenu(int menu_item_id, int quantity) {
        this.menu_item_id = menu_item_id;
        this.quantity = quantity;
    }

    public int getMenu_item_id() {
        return menu_item_id;
    }

    public void setMenu_item_id(int menu_item_id) {
        this.menu_item_id = menu_item_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
