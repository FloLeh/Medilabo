package com.openclassrooms.front.enums;

import lombok.Getter;

@Getter
public enum Gender {
    M("Homme"), F("Femme");

    private final String value;

    Gender(String value) {
        this.value = value;
    }
}
