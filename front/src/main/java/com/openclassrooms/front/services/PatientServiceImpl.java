package com.openclassrooms.front.services;

import com.openclassrooms.front.dto.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final RestTemplate restTemplate;

    public List<Patient> getPatientsList() {
        return restTemplate.getForObject("http://localhost:8080/patients", List.class);
    }

    public void deletePatient(Long id) {
        restTemplate.delete("http://localhost:8080/patient?id=" + id);
    }
}
