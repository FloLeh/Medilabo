package com.openclassrooms.servicereport.clients;

import com.openclassrooms.servicereport.DTOs.MedicalNote;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalNoteClientTest {

    @Mock
    private MedicalNoteClient medicalNoteClient;

    private final MedicalNote MOCK_NOTE_1 = new MedicalNote(
            "1", 1L, "John Doe", "A good note."
    );
    private final MedicalNote MOCK_NOTE_2 = new MedicalNote(
            "2", 2L, "Jane Smith", "Another good note."
    );
    private final List<MedicalNote> MOCK_NOTE_LIST = List.of(MOCK_NOTE_1, MOCK_NOTE_2);

    @Test
    void getNoteList_shouldReturnMockedList() {
        // GIVEN
        when(medicalNoteClient.getNoteList()).thenReturn(MOCK_NOTE_LIST);

        // WHEN
        List<MedicalNote> notes = medicalNoteClient.getNoteList();

        // THEN
        assertNotNull(notes);
        assertEquals(2, notes.size());
        assertEquals("John Doe", notes.getFirst().patient());
    }
}