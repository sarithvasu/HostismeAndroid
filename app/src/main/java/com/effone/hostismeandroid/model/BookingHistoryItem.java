package com.effone.hostismeandroid.model;

/**
 * Created by sumanth.peddinti on 5/5/2017.
 */

public class BookingHistoryItem {
    private  int id;
    private  String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private  String  rest_name;
    private  String  order_id;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public int getTable_no() {
        return table_no;
    }

    public void setTable_no(int table_no) {
        this.table_no = table_no;
    }

    public int getBill_no() {
        return bill_no;
    }

    public void setBill_no(int bill_no) {
        this.bill_no = bill_no;
    }

    public double getBill_ammount() {
        return bill_ammount;
    }

    public void setBill_ammount(double bill_ammount) {
        this.bill_ammount = bill_ammount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private  String  description;
    private  int   table_no;
    private  int   bill_no;
    private  double   bill_ammount;
    private  String  status;

}
