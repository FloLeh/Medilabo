package com.openclassrooms.front.dto;

import java.util.Date;

public record Patient(
        Long id,
        String firstName,
        String lastName,
        Date birthdate,
        String gender,
        String address,
        String phone
) {
}
