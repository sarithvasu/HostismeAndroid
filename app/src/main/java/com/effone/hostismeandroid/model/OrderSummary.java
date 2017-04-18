package com.effone.hostismeandroid.model;

/**
 * Created by sumanth.peddinti on 4/17/2017.
 */

public class OrderSummary {

    private String data_time,rest_name,order_id,description,status;
    int table_no,quantity;
    double total;




    public String getData_time() {
        return data_time;
    }

    public void setData_time(String data_time) {
        this.data_time = data_time;
    }

    public String getRest_name() {
        return rest_name;
    }

    public void setRest_name(String rest_name) {
        this.rest_name = rest_name;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTable_no() {
        return table_no;
    }

    public void setTable_no(int table_no) {
        this.table_no = table_no;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public OrderSummary(String data_time, String rest_name, String order_id, String description, int table_no, int quantity, double total, String status) {
        this.data_time = data_time;
        this.rest_name = rest_name;
        this.order_id = order_id;
        this.description = description;
        this.status = status;
        this.table_no = table_no;
        this.quantity = quantity;

        this.total = total;
    }
}
