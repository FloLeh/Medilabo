package com.openclassrooms.servicepatient.services;

import com.openclassrooms.servicepatient.domains.Patient;
import com.openclassrooms.servicepatient.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

}
