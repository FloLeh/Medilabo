package com.openclassrooms.servicereport.clients;

import com.openclassrooms.servicereport.DTOs.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PatientClientTest {

    private PatientClient patientClient;

    @BeforeEach
    void setUp() {
        patientClient = Mockito.mock(PatientClient.class);
    }

    @Test
    void getPatientsList_shouldReturnList() {
        Patient mockPatient = new Patient(
                1L,
                "John",
                "Doe",
                LocalDate.of(1990, 1, 1),
                "M",
                "123 Street",
                "0123456789"
        );

        when(patientClient.getPatientsList()).thenReturn(List.of(mockPatient));

        List<Patient> patients = patientClient.getPatientsList();

        assertThat(patients).hasSize(1);
        assertThat(patients.getFirst().firstName()).isEqualTo("John");

        verify(patientClient, times(1)).getPatientsList();
    }

}
