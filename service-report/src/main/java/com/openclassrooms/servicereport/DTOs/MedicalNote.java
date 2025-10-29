package com.openclassrooms.servicereport.DTOs;

public record MedicalNote(
        String id,
        Long patientId,
        String patient,
        String note
) {
}
