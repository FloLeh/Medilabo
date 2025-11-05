package com.openclassrooms.front.clients;

import com.openclassrooms.front.dto.MedicalNote;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicalNoteClientTest {

    @Mock
    private MedicalNoteClient medicalNoteClient;

    private static final Long PATIENT_ID = 1L;
    private static final String PATIENT_NAME = "TestPatient";
    private static final String NOTE_CONTENT = "Patient has mild symptoms.";
    private static final MedicalNote MOCK_NOTE = new MedicalNote(
            "665796a583e742111d51a65d",
            PATIENT_ID,
            PATIENT_NAME,
            NOTE_CONTENT
    );

    private static final List<MedicalNote> MOCK_NOTE_LIST = List.of(MOCK_NOTE);

    @Test
    void getNoteList_shouldReturnAllNotes() {
        // GIVEN
        when(medicalNoteClient.getNoteList()).thenReturn(MOCK_NOTE_LIST);

        // WHEN
        List<MedicalNote> actualNotes = medicalNoteClient.getNoteList();

        // THEN
        verify(medicalNoteClient, times(1)).getNoteList();
        assertNotNull(actualNotes);
        assertEquals(1, actualNotes.size());
    }

    @Test
    void getNoteList_shouldReturnEmptyList_whenNoNotesExist() {
        // GIVEN
        when(medicalNoteClient.getNoteList()).thenReturn(Collections.emptyList());

        // WHEN
        List<MedicalNote> actualNotes = medicalNoteClient.getNoteList();

        // THEN
        verify(medicalNoteClient, times(1)).getNoteList();
        assertNotNull(actualNotes);
        assertEquals(0, actualNotes.size());
    }

    @Test
    void getMedicalNotesByPatientId_shouldReturnNotesForSpecificPatient() {
        // GIVEN
        when(medicalNoteClient.getMedicalNotesByPatientId(PATIENT_ID)).thenReturn(MOCK_NOTE_LIST);

        // WHEN
        List<MedicalNote> actualNotes = medicalNoteClient.getMedicalNotesByPatientId(PATIENT_ID);

        // THEN
        verify(medicalNoteClient, times(1)).getMedicalNotesByPatientId(PATIENT_ID);
        assertNotNull(actualNotes);
        assertEquals(1, actualNotes.size());
        assertEquals(NOTE_CONTENT, actualNotes.getFirst().note());
    }

    @Test
    void saveMedicalNote_shouldCallClientSaveMethod() {
        // GIVEN
        doNothing().when(medicalNoteClient).saveMedicalNote(MOCK_NOTE);

        // WHEN
        medicalNoteClient.saveMedicalNote(MOCK_NOTE);

        // THEN
        verify(medicalNoteClient, times(1)).saveMedicalNote(MOCK_NOTE);
    }
}