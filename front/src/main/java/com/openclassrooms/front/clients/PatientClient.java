package com.openclassrooms.front.clients;

import com.openclassrooms.front.config.FeignClientConfig;
import com.openclassrooms.front.dto.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "service-patient", url = "http://localhost:8080", configuration = FeignClientConfig.class)
public interface PatientClient {

    @GetMapping("/patients")
    List<Patient> getPatientsList();

    @GetMapping("/patients/{id}")
    Patient getPatientById(@PathVariable Long id);

    @PostMapping("/patients")
    Patient createPatient(@ModelAttribute Patient patient);

    @PutMapping("/patients/{id}")
    Patient updatePatient(@ModelAttribute Patient patient, @PathVariable Long id);

}
