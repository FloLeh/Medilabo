package com.openclassrooms.servicereport.enums;

public enum Gender {
    MALE("M"),
    FEMALE("F");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
