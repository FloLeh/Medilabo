package com.openclassrooms.front.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record PatientWithReportCodeAndLabel(
        Long id,
        String firstName,
        String lastName,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate birthdate,
        String gender,
        String address,
        String phone,
        int riskCode,
        String riskLabel,
        String riskClass
) {
    public PatientWithReportCodeAndLabel(Patient patient, int riskCode, String riskLabel) {
        this(
                patient.id(),
                patient.firstName(),
                patient.lastName(),
                patient.birthdate(),
                patient.gender().getValue(),
                patient.address(),
                patient.phone(),
                riskCode,
                riskLabel,
                switch (riskCode) {
                    case 2 -> "bg-yellow-200 text-yellow-800";
                    case 3 -> "bg-orange-400 text-white";
                    case 4 -> "bg-red-500 text-white";
                    default -> "bg-gray-200 text-gray-800";
                }
        );
    }
}
