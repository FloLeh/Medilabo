package com.openclassrooms.front.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RiskLevelTest {

    @Test
    void riskLevelConstants_shouldHaveCorrectCodeAndLabel() {
        // GIVEN / WHEN / THEN
        // NONE
        assertEquals(1, RiskLevel.NONE.getCode());
        assertEquals("Aucun risque", RiskLevel.NONE.getLabel());

        // BORDERLINE
        assertEquals(2, RiskLevel.BORDERLINE.getCode());
        assertEquals("Risque limité", RiskLevel.BORDERLINE.getLabel());

        // IN_DANGER
        assertEquals(3, RiskLevel.IN_DANGER.getCode());
        assertEquals("Danger", RiskLevel.IN_DANGER.getLabel());

        // EARLY_ONSET
        assertEquals(4, RiskLevel.EARLY_ONSET.getCode());
        assertEquals("Apparition précoce", RiskLevel.EARLY_ONSET.getLabel());
    }

    @ParameterizedTest
    @CsvSource({
            "1, NONE",
            "2, BORDERLINE",
            "3, IN_DANGER",
            "4, EARLY_ONSET"
    })
    void fromCode_shouldReturnCorrectLevel_whenCodeIsValid(int code, RiskLevel expectedLevel) {
        // GIVEN / WHEN
        RiskLevel actualLevel = RiskLevel.fromCode(code);

        // THEN
        assertNotNull(actualLevel);
        assertEquals(expectedLevel, actualLevel);
        assertEquals(code, actualLevel.getCode());
    }

    @Test
    void fromCode_shouldThrowException_whenCodeIsInvalid() {
        // GIVEN
        int invalidCode = 99;

        // WHEN / THEN
        assertThrows(IllegalArgumentException.class, () -> RiskLevel.fromCode(invalidCode));
    }
}