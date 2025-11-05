package com.openclassrooms.front.clients;

import com.openclassrooms.front.dto.Patient;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientClientTest {

    @Mock
    private PatientClient patientClient;

    private static final Long PATIENT_ID = 1L;

    private static final Patient MOCK_PATIENT = new Patient(
            PATIENT_ID,
            "John",
            "Doe",
            LocalDate.of(1980, 1, 1),
            "M",
            "123 Main St",
            "555-1234"
    );

    private static final List<Patient> MOCK_PATIENT_LIST = List.of(MOCK_PATIENT);

    @Test
    void getPatientsList_shouldReturnAllPatients() {
        // GIVEN
        when(patientClient.getPatientsList()).thenReturn(MOCK_PATIENT_LIST);

        // WHEN
        List<Patient> actualList = patientClient.getPatientsList();

        // THEN
        verify(patientClient, times(1)).getPatientsList();
        assertNotNull(actualList);
        assertEquals(1, actualList.size());
        assertEquals("John", actualList.getFirst().firstName());
    }

    @Test
    void getPatientsList_shouldReturnEmptyList_whenNoPatientsExist() {
        // GIVEN
        when(patientClient.getPatientsList()).thenReturn(Collections.emptyList());

        // WHEN
        List<Patient> actualList = patientClient.getPatientsList();

        // THEN
        verify(patientClient, times(1)).getPatientsList();
        assertNotNull(actualList);
        assertEquals(0, actualList.size());
    }

    @Test
    void getPatientById_shouldReturnPatient_whenFound() {
        // GIVEN
        when(patientClient.getPatientById(PATIENT_ID)).thenReturn(MOCK_PATIENT);

        // WHEN
        Patient actualPatient = patientClient.getPatientById(PATIENT_ID);

        // THEN
        verify(patientClient, times(1)).getPatientById(PATIENT_ID);
        assertNotNull(actualPatient);
        assertEquals(PATIENT_ID, actualPatient.id());
    }

    @Test
    void getPatientById_shouldThrowException_whenNotFound() {
        // GIVEN
        doThrow(FeignException.NotFound.class).when(patientClient).getPatientById(anyLong());

        // WHEN / THEN
        assertThrows(FeignException.NotFound.class, () -> patientClient.getPatientById(99L));
        verify(patientClient, times(1)).getPatientById(99L);
    }

    @Test
    void createPatient_shouldCallClientCreateMethod_andReturnPatient() {
        // GIVEN
        Patient newPatient = new Patient(null, "New", "User", null, "F", null, null);

        Patient createdPatient = new Patient(2L, "New", "User", null, "F", null, null);
        when(patientClient.createPatient(newPatient)).thenReturn(createdPatient);

        // WHEN
        Patient result = patientClient.createPatient(newPatient);

        // THEN
        verify(patientClient, times(1)).createPatient(newPatient);
        assertNotNull(result);
        assertEquals(2L, result.id());
    }

    @Test
    void updatePatient_shouldCallClientUpdateMethod_andReturnUpdatedPatient() {
        // GIVEN
        Patient updateData = new Patient(PATIENT_ID, "Updated", "Doe", null, "M", null, null);

        when(patientClient.updatePatient(updateData, PATIENT_ID)).thenReturn(updateData);

        // WHEN
        Patient result = patientClient.updatePatient(updateData, PATIENT_ID);

        // THEN
        verify(patientClient, times(1)).updatePatient(updateData, PATIENT_ID);
        assertNotNull(result);
        assertEquals("Updated", result.firstName());
        assertEquals(PATIENT_ID, result.id());
    }
}