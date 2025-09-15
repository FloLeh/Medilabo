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

    @GetMapping("/patient")
    public Patient getPatientById(@RequestParam Long id) {
        return patientService.findById(id);
    }

    @PostMapping("/patient")
    public Patient createPatient(@RequestBody Patient patient) {
        return patientService.create(patient);
    }

    @PutMapping("/patient")
    public Patient updatePatient(@RequestBody Patient patient,  @RequestParam Long id) {
        return patientService.update(patient, id);
    }

    @DeleteMapping("/patient")
    public void deletePatient(@RequestParam Long id) {
        patientService.deleteById(id);
    }

}
