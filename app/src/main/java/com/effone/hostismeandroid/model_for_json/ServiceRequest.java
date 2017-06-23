package com.effone.hostismeandroid.model_for_json;

/**
 * Created by sumanth.peddinti on 6/22/2017.
 */

public class ServiceRequest {

    private String restaurant_id;
    private String device_id;
    private String order_id;
    private String table_no;
    private String service_type;
    private String complaint;

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTable_no() {
        return table_no;
    }

    public void setTable_no(String table_no) {
        this.table_no = table_no;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }
}
