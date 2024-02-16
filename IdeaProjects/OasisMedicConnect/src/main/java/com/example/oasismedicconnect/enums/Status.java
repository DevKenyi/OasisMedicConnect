package com.example.oasismedicconnect.enums;

public enum Status {
    Pending("Pending"),
    Shipped("Shipped"),
    Delivered("Delivered"),
    ;

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
