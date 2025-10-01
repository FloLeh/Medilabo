package com.openclassrooms.servicenotes.configurations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.servicenotes.domains.MedicalNote;
import com.openclassrooms.servicenotes.repositories.MedicalNoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.List;

@Configuration
public class JsonDataLoader {

    @Bean
    CommandLineRunner loadData(MedicalNoteRepository repository, ObjectMapper mapper) {
        return args -> {
            repository.deleteAll();
            InputStream input = getClass().getResourceAsStream("/data.json");
            List<MedicalNote> patients = mapper.readValue(input, new TypeReference<>() {});
            repository.saveAll(patients);
        };
    }

}
