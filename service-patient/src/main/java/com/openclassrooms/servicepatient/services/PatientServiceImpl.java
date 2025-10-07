package com.openclassrooms.servicepatient.services;

import com.openclassrooms.servicepatient.domains.Patient;
import com.openclassrooms.servicepatient.repositories.PatientRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public Patient findById(Long id) {
        return patientRepository.findById(id).orElseThrow(NoResultException::new);
    }

    public Patient save(Patient patient) {
        Assert.notNull(patient, "Patient must not be null");
        Assert.notNull(patient.getBirthdate(), "Patient birthdate must not be null");
        Assert.notNull(patient.getGender(), "Patient gender must not be null");

        return patientRepository.save(patient);
    }

    public Patient create(Patient patient) {
        Assert.isNull(patient.getId(), "Patient id must be null");
        return save(patient);
    }

    public Patient update(Patient patient, Long id) {
        Patient patientToUpdate = findById(id);

        patientToUpdate.update(patient);

        return patientRepository.save(patientToUpdate);
    }

}
