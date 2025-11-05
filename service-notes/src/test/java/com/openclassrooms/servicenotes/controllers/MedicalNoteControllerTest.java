package com.openclassrooms.servicenotes.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.servicenotes.domains.MedicalNote;
import com.openclassrooms.servicenotes.services.MedicalNoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MedicalNoteController.class)
class MedicalNoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MedicalNoteService medicalNoteService;

    private static final Long PATIENT_ID = 1L;
    private static final String NOTE_ID = "665796a5";

    private final MedicalNote MOCK_NOTE = new MedicalNote(
            NOTE_ID,
            PATIENT_ID,
            "Test Patient",
            "This is a test note."
    );

    private final List<MedicalNote> MOCK_NOTE_LIST = List.of(MOCK_NOTE);

    @Test
    void getAllMedicalNotes_shouldReturnAllNotes() throws Exception {
        // GIVEN
        when(medicalNoteService.getAllMedicalNotes()).thenReturn(MOCK_NOTE_LIST);

        // WHEN / THEN
        mockMvc.perform(get("/notes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(NOTE_ID));
    }

    @Test
    void getMedicalNotesByPatient_shouldReturnNotesForSpecificPatient() throws Exception {
        // GIVEN
        when(medicalNoteService.getMedicalNotesByPatientId(PATIENT_ID)).thenReturn(MOCK_NOTE_LIST);

        // WHEN / THEN
        mockMvc.perform(get("/notes/{id}", PATIENT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].patientId").value(PATIENT_ID));
    }

    @Test
    void getMedicalNotesByPatient_shouldReturnEmptyList_whenNoNotesFound() throws Exception {
        // GIVEN
        when(medicalNoteService.getMedicalNotesByPatientId(PATIENT_ID)).thenReturn(Collections.emptyList());

        // WHEN / THEN
        mockMvc.perform(get("/notes/{id}", PATIENT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void addMedicalNote_shouldSaveNoteAndReturn200() throws Exception {
        // GIVEN
        MedicalNote noteToAdd = new MedicalNote(null, PATIENT_ID, "New Patient", "New note.");
        String jsonRequest = objectMapper.writeValueAsString(noteToAdd);

        doNothing().when(medicalNoteService).addMedicalNote(any(MedicalNote.class));

        // WHEN
        mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))

                // THEN
                .andExpect(status().isOk());

        verify(medicalNoteService).addMedicalNote(any(MedicalNote.class));
    }
}