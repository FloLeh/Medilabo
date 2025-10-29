package com.openclassrooms.front.enums;

import lombok.Getter;

@Getter
public enum RiskLevel {
    NONE(1, "Aucun risque"),
    BORDERLINE(2, "Risque limité"),
    IN_DANGER(3, "Danger"),
    EARLY_ONSET(4, "Apparition précoce");

    private final int code;
    private final String label;

    RiskLevel(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public static RiskLevel fromCode(int code) {
        for (RiskLevel level : values()) {
            if (level.code == code) return level;
        }
        throw new IllegalArgumentException("Invalid RiskLevel code: " + code);
    }
}
