package com.effone.hostismeandroid.model;

/**
 * Created by sarith.vasu on 02-05-2017.
 */

public class Service {
    private boolean clearTable;
    private boolean callWaiter;

    public boolean isClearTable() {
        return clearTable;
    }

    public void setClearTable(boolean clearTable) {
        this.clearTable = clearTable;
    }

    public boolean isCallWaiter() {
        return callWaiter;
    }

    public void setCallWaiter(boolean callWaiter) {
        this.callWaiter = callWaiter;
    }
}
