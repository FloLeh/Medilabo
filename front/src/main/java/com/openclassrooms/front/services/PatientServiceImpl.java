package com.openclassrooms.front.services;

import com.openclassrooms.front.clients.PatientClient;
import com.openclassrooms.front.dto.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientClient patientClient;

    public List<Patient> getPatientsList() {
        return patientClient.getPatientsList();
    }

    public Patient getPatientById(Long id) {
        return patientClient.getPatientById(id);
    }

    public Patient createPatient(Patient patient) {
        return patientClient.createPatient(patient);
    }

    public Patient updatePatient(Patient patient, Long id) {
        return patientClient.updatePatient(patient, id);
    }

    public void deletePatient(Long id) {
        patientClient.deletePatient(id);
    }
}
