package com.effone.hostismeandroid.model;

/**
 * Created by sarith.vasu on 31-05-2017.
 */

public class PaymentConfirmation {
    private String bill_no;
    private String bill_ammount;
    private String bill_date;
    private String order_id;
    private String Description;
    private String Table_no;
    private String RestName;
    private String Status;

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getBill_ammount() {
        return bill_ammount;
    }

    public void setBill_ammount(String bill_ammount) {
        this.bill_ammount = bill_ammount;
    }

    public String getBill_date() {
        return bill_date;
    }

    public void setBill_date(String bill_date) {
        this.bill_date = bill_date;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTable_no() {
        return Table_no;
    }

    public void setTable_no(String table_no) {
        Table_no = table_no;
    }

    public String getRestName() {
        return RestName;
    }

    public void setRestName(String restName) {
        RestName = restName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
