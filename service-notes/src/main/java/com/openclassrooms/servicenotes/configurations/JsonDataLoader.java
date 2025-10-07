package com.openclassrooms.servicenotes.configurations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.servicenotes.domains.MedicalNote;
import com.openclassrooms.servicenotes.repositories.MedicalNoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.InputStream;
import java.util.List;

@Configuration
public class JsonDataLoader {

    @Bean
    CommandLineRunner loadData(final MedicalNoteRepository repository, final ObjectMapper mapper) {
        return args -> {
            repository.deleteAll();

            final ResourceLoader resourceLoader = new DefaultResourceLoader();
            final InputStream resource = resourceLoader.getResource("classpath:data.json").getInputStream();
            List<MedicalNote> patients = mapper.readValue(resource, new TypeReference<>() {});

            repository.saveAll(patients);
        };
    }

}
