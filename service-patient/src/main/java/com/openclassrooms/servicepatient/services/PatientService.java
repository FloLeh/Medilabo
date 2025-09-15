package com.openclassrooms.servicepatient.services;


import com.openclassrooms.servicepatient.domains.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> findAll();
}
