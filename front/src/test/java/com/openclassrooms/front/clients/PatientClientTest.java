package com.openclassrooms.front.clients;

import com.openclassrooms.front.dto.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PatientClientUnitTest {

    private PatientClient patientClient;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeEach
    void setUp() {
        patientClient = Mockito.mock(PatientClient.class);
    }

    @Test
    void getPatientById_shouldReturnPatient() throws Exception {
        Patient mockPatient = new Patient(
                1L,
                "John",
                "Doe",
                sdf.parse("2000-01-01"),
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
        assertThat(sdf.format(patient.birthdate())).isEqualTo("2000-01-01");

        verify(patientClient, times(1)).getPatientById(1L);
    }

    @Test
    void getPatientsList_shouldReturnList() throws Exception {
        Patient mockPatient = new Patient(
                1L,
                "John",
                "Doe",
                sdf.parse("2000-01-01"),
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
    void createPatient_shouldReturnCreatedPatient() throws Exception {
        Patient newPatient = new Patient(
                null,
                "Alice",
                "Smith",
                sdf.parse("1995-05-05"),
                "F",
                "",
                ""
        );

        Patient createdPatient = new Patient(
                2L,
                "Alice",
                "Smith",
                sdf.parse("1995-05-05"),
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
    void updatePatient_shouldReturnUpdatedPatient() throws Exception {
        Patient updatedPatient = new Patient(
                1L,
                "John",
                "Updated",
                sdf.parse("2000-01-01"),
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
