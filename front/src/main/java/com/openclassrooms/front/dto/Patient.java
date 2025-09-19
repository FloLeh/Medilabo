package com.openclassrooms.front.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public record Patient(
        Long id,
        String firstName,
        String lastName,
        @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdate,
        String gender,
        String address,
        String phone
) {

    public String formattedBirthdate() {
        return birthdate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

}
