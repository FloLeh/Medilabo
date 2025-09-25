package com.openclassrooms.front.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record Patient(
        Long id,
        String firstName,
        String lastName,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate birthdate,
        String gender,
        String address,
        String phone
) {

}
