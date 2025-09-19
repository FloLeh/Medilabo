package com.openclassrooms.front.services;

import com.openclassrooms.front.dto.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> getPatientsList();
    void deletePatient(Long id);
}
