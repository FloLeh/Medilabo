package com.openclassrooms.front.clients;

import com.openclassrooms.front.config.FeignClientConfig;
import com.openclassrooms.front.dto.MedicalNote;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "service-notes", url = "http://localhost:8080", configuration = FeignClientConfig.class)
public interface MedicalNoteClient {

    @GetMapping("/notes")
    List<MedicalNote> getNoteList();

    @GetMapping("/notes/{id}")
    List<MedicalNote> getMedicalNotesByPatientId(@PathVariable Long id);

    @PostMapping("/notes")
    void saveMedicalNote(@ModelAttribute MedicalNote medicalNote);

}
