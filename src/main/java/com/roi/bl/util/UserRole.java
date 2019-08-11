package com.roi.bl.util;

public enum UserRole {
    ADMIN(1),
    ROOT_USER(2),
    CHILD_USER(3);

    private int getValue;

    public int getGetValue() {
        return this.getValue;
    }

    UserRole(int i) {
        this.getValue = i;
    }



}
