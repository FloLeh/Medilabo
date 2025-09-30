package com.openclassrooms.servicenotes.controllers;

import com.openclassrooms.servicenotes.domains.MedicalNote;
import com.openclassrooms.servicenotes.services.MedicalNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MedicalNoteController {

    private final MedicalNoteService medicalNoteService;

    @GetMapping("/notes")
    public List<MedicalNote> getMedicalNotes() {
        return medicalNoteService.getMedicalNotes();
    }

}
