package com.openclassrooms.front.services;

import com.openclassrooms.front.clients.MedicalNoteClient;
import com.openclassrooms.front.dto.MedicalNote;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicalNoteServiceTest {

    @Mock
    private MedicalNoteClient medicalNoteClient;

    @InjectMocks
    private MedicalNoteServiceImpl medicalNoteService;

    private static final Long PATIENT_ID = 1L;
    private static final String PATIENT_NAME = "TestPatient";
    private static final String NOTE_CONTENT = "Patient has symptoms.";
    private static final MedicalNote MOCK_NOTE = new MedicalNote(
            "665796a583e742111d51a65d",
            PATIENT_ID,
            PATIENT_NAME,
            NOTE_CONTENT
    );

    @Test
    void getMedicalNoteByPatientId_shouldReturnNotes_whenClientReturnsData() {
        // GIVEN
        List<MedicalNote> expectedNotes = List.of(MOCK_NOTE);
        when(medicalNoteClient.getMedicalNotesByPatientId(PATIENT_ID)).thenReturn(expectedNotes);

        // WHEN
        List<MedicalNote> actualNotes = medicalNoteService.getMedicalNoteByPatientId(PATIENT_ID);

        // THEN
        verify(medicalNoteClient, times(1)).getMedicalNotesByPatientId(PATIENT_ID);
        assertNotNull(actualNotes);
        assertEquals(1, actualNotes.size());
        assertEquals(NOTE_CONTENT, actualNotes.getFirst().note());
    }

    @Test
    void getMedicalNoteByPatientId_shouldReturnEmptyList_whenNoNotesFound() {
        // GIVEN
        List<MedicalNote> expectedNotes = Collections.emptyList();
        when(medicalNoteClient.getMedicalNotesByPatientId(PATIENT_ID)).thenReturn(expectedNotes);

        // WHEN
        List<MedicalNote> actualNotes = medicalNoteService.getMedicalNoteByPatientId(PATIENT_ID);

        // THEN
        verify(medicalNoteClient, times(1)).getMedicalNotesByPatientId(PATIENT_ID);
        assertNotNull(actualNotes);
        assertEquals(0, actualNotes.size());
    }



    @Test
    void saveMedicalNote_shouldCallClientSaveMethod_withCorrectNote() {
        // GIVEN
        doNothing().when(medicalNoteClient).saveMedicalNote(MOCK_NOTE);

        // WHEN
        medicalNoteService.saveMedicalNote(MOCK_NOTE);

        // THEN
        verify(medicalNoteClient, times(1)).saveMedicalNote(MOCK_NOTE);
    }
}