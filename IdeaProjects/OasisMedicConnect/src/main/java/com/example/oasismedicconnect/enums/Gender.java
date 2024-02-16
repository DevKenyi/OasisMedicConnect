package com.example.oasismedicconnect.enums;

import lombok.Getter;

@Getter
public enum Gender {
    Male("Male"),
    FEMALE("Female");

    private String gender;

    Gender(String gender) {
        this.gender = gender;
    }


}
