package com.openclassrooms.servicereport.enums;

public enum RiskLevel {
    NONE(1),
    BORDERLINE(2),
    IN_DANGER(3),
    EARLY_ONSET(4);

    private final int code;

    RiskLevel(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }

}