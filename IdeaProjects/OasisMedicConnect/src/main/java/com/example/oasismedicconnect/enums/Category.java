package com.example.oasismedicconnect.enums;

public enum Category {
    Surgical("Surgical"),
    Neonatal("Neonatal"),
    Laboratory("Laboratory"),
    ;

    private String category;

    Category(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
