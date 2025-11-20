package com.openclassrooms.front.services;

import com.openclassrooms.front.clients.PatientClient;
import com.openclassrooms.front.dto.Patient;
import com.openclassrooms.front.enums.Gender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientClient patientClient;

    @InjectMocks
    private PatientServiceImpl patientService;

    private static final Long PATIENT_ID = 1L;

    private static final Patient MOCK_PATIENT = new Patient(
            PATIENT_ID,
            "John",
            "Doe",
            LocalDate.of(1980, 1, 1),
            Gender.M,
            "123 Main St",
            "555-1234"
    );

    private static final List<Patient> MOCK_PATIENT_LIST = List.of(MOCK_PATIENT);

    @Test
    void getPatientsList_shouldReturnListFromClient() {
        // GIVEN
        when(patientClient.getPatientsList()).thenReturn(MOCK_PATIENT_LIST);

        // WHEN
        List<Patient> actualList = patientService.getPatientsList();

        // THEN
        verify(patientClient, times(1)).getPatientsList();
        assertNotNull(actualList);
        assertEquals(1, actualList.size());
        assertEquals("John", actualList.getFirst().firstName());
    }

    @Test
    void getPatientById_shouldReturnPatient_whenFound() {
        // GIVEN
        when(patientClient.getPatientById(PATIENT_ID)).thenReturn(MOCK_PATIENT);

        // WHEN
        Patient actualPatient = patientService.getPatientById(PATIENT_ID);

        // THEN
        verify(patientClient, times(1)).getPatientById(PATIENT_ID);
        assertNotNull(actualPatient);
        assertEquals(PATIENT_ID, actualPatient.id());
    }

    @Test
    void createPatient_shouldCallClientCreateMethod_withCorrectPatient() {
        // GIVEN
        when(patientClient.createPatient(MOCK_PATIENT)).thenReturn(MOCK_PATIENT);

        // WHEN
        patientService.createPatient(MOCK_PATIENT);

        // THEN
        verify(patientClient, times(1)).createPatient(MOCK_PATIENT);
    }

    @Test
    void updatePatient_shouldCallClientUpdateMethod_withCorrectPatientAndId() {
        // GIVEN
        Patient updatedPatientData = new Patient(
                PATIENT_ID,
                "Jane",
                "Doe",
                LocalDate.of(1985, 5, 5),
                Gender.F,
                "456 Other Blvd",
                "555-4321"
        );

        when(patientClient.updatePatient(updatedPatientData, PATIENT_ID)).thenReturn(updatedPatientData);

        // WHEN
        patientService.updatePatient(updatedPatientData, PATIENT_ID);

        // THEN
        verify(patientClient, times(1)).updatePatient(updatedPatientData, PATIENT_ID);
    }
}