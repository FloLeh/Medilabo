package com.openclassrooms.front.dto;

public record MedicalNote(
        String id,
        Long patientId,
        String patient,
        String note
) {
}
