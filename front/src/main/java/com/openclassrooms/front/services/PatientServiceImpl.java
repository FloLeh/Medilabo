package com.openclassrooms.front.services;

import com.openclassrooms.front.dto.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final RestTemplate restTemplate;

    public List<Patient> getPatientsList() {
        // format différent des autres pour utiliser le formattedBirthDate
        ResponseEntity<List<Patient>> response = restTemplate.exchange(
                "http://localhost:8080/patients",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Patient>>() {}
        );
        return response.getBody();
    }

    public Patient getPatientById(Long id) {
        return restTemplate.getForObject("http://localhost:8080/patient?id=" + id, Patient.class);
    }

    public void createPatient(Patient patient) {
        restTemplate.postForObject("http://localhost:8080/patient", patient, Patient.class);
    }

    public void updatePatient(Patient patient, Long id) {
        restTemplate.put("http://localhost:8080/patient?id=" + id, patient);
    }

    public void deletePatient(Long id) {
        restTemplate.delete("http://localhost:8080/patient?id=" + id);
    }
}
