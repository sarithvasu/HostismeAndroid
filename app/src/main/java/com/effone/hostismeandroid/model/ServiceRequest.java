package com.effone.hostismeandroid.model;

/**
 * Created by sarith.vasu on 02-05-2017.
 */

public class ServiceRequest {
    private Complaint complaint;
    private MoveTable moveTable;
    private Service service;

    public Complaint getComplaint() {
        return complaint;
    }

    public void setComplaint(Complaint complaint) {
        this.complaint = complaint;
    }

    public MoveTable getMoveTable() {
        return moveTable;
    }

    public void setMoveTable(MoveTable moveTable) {
        this.moveTable = moveTable;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
