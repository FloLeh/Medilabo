package com.openclassrooms.front.clients;

import com.openclassrooms.front.dto.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "service-patient", url = "http://localhost:8080")
public interface PatientClient {

    @GetMapping("/patients")
    List<Patient> getPatientsList();

    @GetMapping("/patient")
    Patient getPatientById(@RequestParam Long id);

    @PostMapping("/patient")
    void createPatient(@ModelAttribute Patient patient);

    @PutMapping("/patient")
    void updatePatient(@ModelAttribute Patient patient, @RequestParam Long id);

    @DeleteMapping("/patient")
    void deletePatient(@RequestParam Long id);

}
