package com.openclassrooms.servicenotes.configurations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.servicenotes.domains.MedicalNote;
import com.openclassrooms.servicenotes.repositories.MedicalNoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.CommandLineRunner;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JsonDataLoaderTest {

    @Mock
    private MedicalNoteRepository repository;

    @Mock
    private ObjectMapper mapper;

    private final JsonDataLoader jsonDataLoader = new JsonDataLoader();

    private final List<MedicalNote> MOCK_NOTES = List.of(
            new MedicalNote("1", 1L, "Test Patient", "Note 1")
    );

    @Test
    void loadData_shouldLoadAndSaveNotes() throws Exception {
        // GIVEN
        when(mapper.readValue(
                any(InputStream.class),
                any(TypeReference.class))
        ).thenReturn(MOCK_NOTES);

        // WHEN
        CommandLineRunner runner = jsonDataLoader.loadData(repository, mapper);
        runner.run();

        // THEN
        verify(repository, times(1)).deleteAll();
        verify(mapper, times(1)).readValue(any(InputStream.class), any(TypeReference.class));
        verify(repository, times(1)).saveAll(MOCK_NOTES);
    }

    @Test
    void loadData_shouldPropagateException_whenReadingFails() throws Exception {
        // GIVEN
        when(mapper.readValue(any(InputStream.class), any(TypeReference.class)))
                .thenThrow(new RuntimeException("JSON read error"));

        // WHEN
        CommandLineRunner runner = jsonDataLoader.loadData(repository, mapper);

        // THEN
        assertThrows(RuntimeException.class, runner::run);

        verify(repository, times(1)).deleteAll();
        verify(repository, never()).saveAll(any());
    }
}