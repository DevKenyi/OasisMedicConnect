package com.example.oasismedicconnect.enums;

public enum Roles {

    Admin("Admin"),
    Supplier("Supplier"),
    Customer("Customer")

    ;

    private String roles;

    Roles(String roles) {
        this.roles = roles;
    }

    public String getRoles() {
        return roles;
    }
}
