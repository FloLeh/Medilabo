package com.openclassrooms.servicereport.clients;

import com.openclassrooms.servicereport.DTOs.Patient;
import com.openclassrooms.servicereport.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "service-patient", url = "${gateway.url}", configuration = FeignClientConfig.class)
public interface PatientClient {

    @GetMapping("/patients")
    List<Patient> getPatientsList();

}
