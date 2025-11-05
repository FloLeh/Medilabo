package com.openclassrooms.front.controllers;

import com.openclassrooms.front.dto.MedicalNote;
import com.openclassrooms.front.dto.Patient;
import com.openclassrooms.front.services.MedicalNoteService;
import com.openclassrooms.front.services.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(MedicalNoteController.class)
class MedicalNoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MedicalNoteService medicalNoteService;

    @MockitoBean
    private PatientService patientService;

    private static final Long PATIENT_ID = 1L;
    private static final String NOTE_ID = "123abc";

    private static final Patient MOCK_PATIENT = new Patient(
            PATIENT_ID, "John", "Doe", LocalDate.of(1980, 1, 1), "M", "123 Main St", "555-1234"
    );

    private static final MedicalNote MOCK_NOTE = new MedicalNote(
            NOTE_ID, PATIENT_ID, "John Doe", "Patient condition is stable."
    );

    private static final List<MedicalNote> MOCK_NOTE_LIST = List.of(MOCK_NOTE);

    @Test
    void showMedicalNote_shouldDisplayNotesAndPatient() throws Exception {
        // GIVEN
        when(medicalNoteService.getMedicalNoteByPatientId(PATIENT_ID)).thenReturn(MOCK_NOTE_LIST);
        when(patientService.getPatientById(PATIENT_ID)).thenReturn(MOCK_PATIENT);

        // WHEN / THEN
        mockMvc.perform(get("/patients/{patientId}/notes", PATIENT_ID))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/notes"))
                .andExpect(model().attribute("patient", MOCK_PATIENT))
                .andExpect(model().attribute("medicalNotes", MOCK_NOTE_LIST));
    }

    @Test
    void showMedicalNote_shouldHandleNoNotesFound() throws Exception {
        // GIVEN
        when(medicalNoteService.getMedicalNoteByPatientId(PATIENT_ID)).thenReturn(Collections.emptyList());
        when(patientService.getPatientById(PATIENT_ID)).thenReturn(MOCK_PATIENT);

        // WHEN / THEN
        mockMvc.perform(get("/patients/{patientId}/notes", PATIENT_ID))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/notes"))
                .andExpect(model().attribute("medicalNotes", Collections.emptyList()));
    }

    @Test
    void saveMedicalNote_shouldSaveNoteAndRedirect() throws Exception {
        // GIVEN
        doNothing().when(medicalNoteService).saveMedicalNote(any(MedicalNote.class));

        // WHEN
        mockMvc.perform(post("/patients/{patientId}/notes", PATIENT_ID)
                        .param("patientId", PATIENT_ID.toString())
                        .param("patient", "Test User")
                        .param("note", "New note content"))

                // THEN
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/patients/" + PATIENT_ID + "/notes"));

        verify(medicalNoteService).saveMedicalNote(any(MedicalNote.class));
    }
}