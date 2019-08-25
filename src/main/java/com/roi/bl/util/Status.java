package com.roi.bl.util;

public enum Status {

    INACTIVE(0),
    ACTIVE(1);

    private int statusValue;

    public int getStatusValue() {
        return this.statusValue;
    }

    Status(Integer integer){
        this.statusValue = integer;

    }
}
