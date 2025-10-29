package com.openclassrooms.servicereport.DTOs;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public record PatientWithNotes(
        Long id,
        String firstName,
        String lastName,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate birthdate,
        String gender,
        String address,
        String phone,
        List<String> notes
) {
        public int getAge() {
                return LocalDate.now().getYear() - birthdate.getYear();
        }
}
