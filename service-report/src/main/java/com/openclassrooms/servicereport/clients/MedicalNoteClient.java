package com.openclassrooms.servicereport.clients;

import com.openclassrooms.servicereport.DTOs.MedicalNote;
import com.openclassrooms.servicereport.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "service-notes", url = "http://localhost:8080", configuration = FeignClientConfig.class)
public interface MedicalNoteClient {

    @GetMapping("/notes")
    List<MedicalNote> getNoteList();

}
