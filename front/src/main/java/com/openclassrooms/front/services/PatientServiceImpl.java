package com.openclassrooms.front.services;

import com.openclassrooms.front.domains.Patient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    public List<Patient> getPatientsList() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://localhost:8080/patients", List.class);
    }
}
