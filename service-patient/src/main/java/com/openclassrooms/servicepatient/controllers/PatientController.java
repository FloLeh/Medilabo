package com.openclassrooms.servicepatient.controllers;

import com.openclassrooms.servicepatient.domains.Patient;
import com.openclassrooms.servicepatient.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
        return patientService.findAll();
    }

}
