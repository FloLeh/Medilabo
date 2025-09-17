package com.openclassrooms.front.services;

import com.openclassrooms.front.domains.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> getPatientsList();
}
