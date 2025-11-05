package com.openclassrooms.servicereport.clients;

import com.openclassrooms.servicereport.DTOs.Patient;
import com.openclassrooms.servicereport.enums.Gender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientClientTest {

    @Mock
    private PatientClient patientClient;

    private final Patient MOCK_PATIENT_1 = new Patient(
            1L,
            "John",
            "Doe",
            LocalDate.of(1980, 1, 1),
            Gender.MALE.value(),
            "1 Main St",
            "555-1234"
    );
    private final Patient MOCK_PATIENT_2 = new Patient(
            2L,
            "Jane",
            "Smith",
            LocalDate.of(1995, 5, 15),
            Gender.FEMALE.value(),
            "2 Second Ave",
            "555-5678"
    );
    private final List<Patient> MOCK_PATIENT_LIST = List.of(MOCK_PATIENT_1, MOCK_PATIENT_2);

    @Test
    void getPatientsList_shouldReturnMockedList() {
        // GIVEN
        when(patientClient.getPatientsList()).thenReturn(MOCK_PATIENT_LIST);

        // WHEN
        List<Patient> patients = patientClient.getPatientsList();

        // THEN
        assertNotNull(patients);
        assertEquals(2, patients.size());
        assertEquals("John", patients.get(0).firstName());
        assertEquals(2L, patients.get(1).id());
    }
}