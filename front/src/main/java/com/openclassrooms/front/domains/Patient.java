package com.openclassrooms.front.domains;

import lombok.Data;

import java.util.Date;

@Data
public class Patient {

    private Long id;

    private String firstName;

    private String lastName;

    private Date birthdate;

    private String gender;

    private String address;

    private String phone;

}
