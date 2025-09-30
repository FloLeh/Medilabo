package com.openclassrooms.servicepatient.controllers;

import com.openclassrooms.servicepatient.domains.Patient;
import com.openclassrooms.servicepatient.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
        return patientService.findAll();
    }

    @GetMapping("/patients/{id}")
    public Patient getPatientById(@PathVariable Long id) {
        return patientService.findById(id);
    }

    @PostMapping("/patients")
    public Patient createPatient(@RequestBody Patient patient) {
        return patientService.create(patient);
    }

    @PutMapping("/patients/{id}")
    public Patient updatePatient(@RequestBody Patient patient,  @PathVariable Long id) {
        return patientService.update(patient, id);
    }

    @DeleteMapping("/patients/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.deleteById(id);
    }

}
