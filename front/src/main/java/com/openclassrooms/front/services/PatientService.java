package com.openclassrooms.front.services;

import com.openclassrooms.front.dto.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> getPatientsList();
    Patient getPatientById(Long id);
    Patient createPatient(Patient patient);
    Patient updatePatient(Patient patient, Long id);
    void deletePatient(Long id);
}
