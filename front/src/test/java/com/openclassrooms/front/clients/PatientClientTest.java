package com.openclassrooms.front.clients;

import com.openclassrooms.front.dto.Patient;
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
    void getPatientById_shouldReturnPatient() {
        Patient mockPatient = new Patient(
                1L,
                "John",
                "Doe",
                LocalDate.of(2000, 1, 1),
                "M",
                "123 Street",
                "0123456789"
        );

        when(patientClient.getPatientById(1L)).thenReturn(mockPatient);

        Patient patient = patientClient.getPatientById(1L);

        assertThat(patient.id()).isEqualTo(1L);
        assertThat(patient.firstName()).isEqualTo("John");
        assertThat(patient.lastName()).isEqualTo("Doe");
        assertThat(patient.gender()).isEqualTo("M");
        assertThat(patient.address()).isEqualTo("123 Street");
        assertThat(patient.phone()).isEqualTo("0123456789");
        assertThat(patient.birthdate()).isEqualTo("2000-01-01");

        verify(patientClient, times(1)).getPatientById(1L);
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

    @Test
    void createPatient_shouldReturnCreatedPatient() {
        Patient newPatient = new Patient(
                null,
                "Alice",
                "Smith",
                LocalDate.of(1995, 5, 5),
                "F",
                "",
                ""
        );

        Patient createdPatient = new Patient(
                2L,
                "Alice",
                "Smith",
                LocalDate.of(1995, 5, 5),
                "F",
                "",
                ""
        );

        when(patientClient.createPatient(newPatient)).thenReturn(createdPatient);

        Patient result = patientClient.createPatient(newPatient);

        assertThat(result.id()).isEqualTo(2L);
        assertThat(result.firstName()).isEqualTo("Alice");
        assertThat(result.address()).isEmpty();
        assertThat(result.phone()).isEmpty();

        verify(patientClient, times(1)).createPatient(newPatient);
    }

    @Test
    void updatePatient_shouldReturnUpdatedPatient() {
        Patient updatedPatient = new Patient(
                1L,
                "John",
                "Updated",
                LocalDate.of(2000, 1, 1),
                "M",
                "123 Street",
                "0123456789"
        );

        when(patientClient.updatePatient(updatedPatient, 1L)).thenReturn(updatedPatient);

        Patient result = patientClient.updatePatient(updatedPatient, 1L);

        assertThat(result.lastName()).isEqualTo("Updated");

        verify(patientClient, times(1)).updatePatient(updatedPatient, 1L);
    }

    @Test
    void deletePatient_shouldCallEndpoint() {
        doNothing().when(patientClient).deletePatient(1L);

        patientClient.deletePatient(1L);

        verify(patientClient, times(1)).deletePatient(1L);
    }
}
