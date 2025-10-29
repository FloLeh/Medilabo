package com.openclassrooms.servicereport.enums;

import java.util.Arrays;
import java.util.List;

public enum TriggerWords {
    HEMOGLOBINE_A1C("hémoglobine a1c"),
    MICROALBUMINE("microalbumine"),
    TAILLE("taille"),
    POIDS("poids"),
    FUMEUR("fumeur", "fumeuse"),
    ANORMAL("anormal"),
    CHOLESTEROL("cholestérol"),
    VERTIGES("vertiges", "vertige"),
    RECHUTE("rechute"),
    REACTION("réaction"),
    ANTICORPS("anticorps");

    private final List<String> keywords;

    TriggerWords(String... keywords) {
        this.keywords = Arrays.stream(keywords)
                .map(String::toLowerCase)
                .toList();
    }

    public boolean matches(String note) {
        if (note == null || note.isBlank()) return false;
        String lowerNote = note.toLowerCase();
        return keywords.stream().anyMatch(lowerNote::contains);
    }

    public static long countTriggers(String note) {
        return Arrays.stream(values())
                .filter(trigger -> trigger.matches(note))
                .count();
    }
}
