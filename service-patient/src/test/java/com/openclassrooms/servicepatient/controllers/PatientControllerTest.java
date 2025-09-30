package com.openclassrooms.servicepatient.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.servicepatient.domains.Patient;
import com.openclassrooms.servicepatient.services.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PatientService patientService;

    @Autowired
    private ObjectMapper objectMapper;

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setGender("M");
        patient.setBirthdate(LocalDate.of(1990, 1, 1));
    }

    @Test
    void getAllPatients_ShouldReturnList() throws Exception {
        List<Patient> patients = List.of(patient);
        when(patientService.findAll()).thenReturn(patients);

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    void getPatientById_ShouldReturnPatient() throws Exception {
        when(patientService.findById(1L)).thenReturn(patient);

        mockMvc.perform(get("/patients/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void createPatient_ShouldReturnCreatedPatient() throws Exception {
        when(patientService.create(any(Patient.class))).thenReturn(patient);

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(patientService).create(any(Patient.class));
    }

    @Test
    void updatePatient_ShouldReturnUpdatedPatient() throws Exception {
        when(patientService.update(any(Patient.class), eq(1L))).thenReturn(patient);

        mockMvc.perform(put("/patients/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));

        verify(patientService).update(any(Patient.class), eq(1L));
    }

    @Test
    void deletePatient_ShouldCallService() throws Exception {
        mockMvc.perform(delete("/patients/{id}", 1L))
                .andExpect(status().isOk());

        verify(patientService).deleteById(1L);
    }
}
