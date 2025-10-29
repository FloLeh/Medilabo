package com.openclassrooms.servicereport.DTOs;

public record PatientReportResponse(
        Long patientId,
        int riskCode
) {
}
