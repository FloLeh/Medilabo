package com.openclassrooms.servicepatient.domains;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate birthdate;

    private String gender;

    private String address;

    private String phone;

    public void update(Patient patient) {
        firstName = patient.getFirstName();
        lastName = patient.getLastName();
        birthdate = patient.getBirthdate();
        gender = patient.getGender();
        address = patient.getAddress();
        phone = patient.getPhone();
    }

}
