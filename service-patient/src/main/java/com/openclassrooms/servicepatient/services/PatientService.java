package com.openclassrooms.servicepatient.services;


import com.openclassrooms.servicepatient.domains.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> findAll();
    Patient findById(Long id);
    Patient save(Patient patient);
    Patient create(Patient patient);
    Patient update(Patient patient,  Long id);
    void deleteById(Long id);
}
