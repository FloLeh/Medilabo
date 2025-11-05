package com.openclassrooms.servicenotes.services;

import com.openclassrooms.servicenotes.domains.MedicalNote;
import com.openclassrooms.servicenotes.repositories.MedicalNoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicalNoteServiceTest {

    @Mock
    private MedicalNoteRepository medicalNoteRepository;

    @InjectMocks
    private MedicalNoteServiceImpl medicalNoteService;

    private static final Long PATIENT_ID = 1L;
    private static final String NOTE_ID = "123abc";

    private final MedicalNote VALID_NOTE = new MedicalNote(
            null,
            PATIENT_ID,
            "John Doe",
            "Test Note Content"
    );

    private final MedicalNote SAVED_NOTE = new MedicalNote(
            NOTE_ID,
            PATIENT_ID,
            "John Doe",
            "Test Note Content"
    );

    private final List<MedicalNote> MOCK_NOTE_LIST = List.of(SAVED_NOTE);

    @Test
    void getAllMedicalNotes_shouldReturnAllNotes() {
        // GIVEN
        when(medicalNoteRepository.findAll()).thenReturn(MOCK_NOTE_LIST);

        // WHEN
        List<MedicalNote> actualNotes = medicalNoteService.getAllMedicalNotes();

        // THEN
        verify(medicalNoteRepository, times(1)).findAll();
        assertNotNull(actualNotes);
        assertEquals(1, actualNotes.size());
    }

    @Test
    void getMedicalNotesByPatientId_shouldReturnNotesForSpecificPatient() {
        // GIVEN
        when(medicalNoteRepository.findByPatientId(PATIENT_ID)).thenReturn(MOCK_NOTE_LIST);

        // WHEN
        List<MedicalNote> actualNotes = medicalNoteService.getMedicalNotesByPatientId(PATIENT_ID);

        // THEN
        verify(medicalNoteRepository, times(1)).findByPatientId(PATIENT_ID);
        assertNotNull(actualNotes);
        assertEquals(1, actualNotes.size());
    }

    @Test
    void getMedicalNotesByPatientId_shouldReturnEmptyList_whenNoNotesFound() {
        // GIVEN
        when(medicalNoteRepository.findByPatientId(PATIENT_ID)).thenReturn(Collections.emptyList());

        // WHEN
        List<MedicalNote> actualNotes = medicalNoteService.getMedicalNotesByPatientId(PATIENT_ID);

        // THEN
        verify(medicalNoteRepository, times(1)).findByPatientId(PATIENT_ID);
        assertNotNull(actualNotes);
        assertEquals(0, actualNotes.size());
    }

    @Test
    void addMedicalNote_shouldSaveNote_whenNoteIsValid() {
        // GIVEN
        when(medicalNoteRepository.save(any(MedicalNote.class))).thenReturn(SAVED_NOTE);

        // WHEN
        medicalNoteService.addMedicalNote(VALID_NOTE);

        // THEN
        verify(medicalNoteRepository, times(1)).save(VALID_NOTE);
    }

    @Test
    void addMedicalNote_shouldThrowIllegalArgumentException_whenNoteIsNull() {
        // GIVEN / WHEN / THEN
        assertThrows(IllegalArgumentException.class, () ->
                medicalNoteService.addMedicalNote(null)
        );
        verify(medicalNoteRepository, never()).save(any());
    }

    @Test
    void addMedicalNote_shouldThrowIllegalArgumentException_whenIdIsNotNull() {
        // GIVEN
        MedicalNote invalidNote = new MedicalNote(NOTE_ID, PATIENT_ID, "John Doe", "Content");

        // WHEN / THEN
        assertThrows(IllegalArgumentException.class, () ->
                medicalNoteService.addMedicalNote(invalidNote)
        );
        verify(medicalNoteRepository, never()).save(any());
    }

    @Test
    void addMedicalNote_shouldThrowIllegalArgumentException_whenPatientIdIsNull() {
        // GIVEN
        MedicalNote invalidNote = new MedicalNote(null, null, "John Doe", "Content");

        // WHEN / THEN
        assertThrows(IllegalArgumentException.class, () ->
                medicalNoteService.addMedicalNote(invalidNote)
        );
        verify(medicalNoteRepository, never()).save(any());
    }

    @Test
    void addMedicalNote_shouldThrowIllegalArgumentException_whenPatientIsNull() {
        // GIVEN
        MedicalNote invalidNote = new MedicalNote(null, PATIENT_ID, null, "Content");

        // WHEN / THEN
        assertThrows(IllegalArgumentException.class, () ->
                medicalNoteService.addMedicalNote(invalidNote)
        );
        verify(medicalNoteRepository, never()).save(any());
    }

    @Test
    void addMedicalNote_shouldThrowIllegalArgumentException_whenNoteContentIsNull() {
        // GIVEN
        MedicalNote invalidNote = new MedicalNote(null, PATIENT_ID, "John Doe", null);

        // WHEN / THEN
        assertThrows(IllegalArgumentException.class, () ->
                medicalNoteService.addMedicalNote(invalidNote)
        );
        verify(medicalNoteRepository, never()).save(any());
    }
}