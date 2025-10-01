package com.openclassrooms.servicenotes.controllers;

import com.openclassrooms.servicenotes.domains.MedicalNote;
import com.openclassrooms.servicenotes.services.MedicalNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MedicalNoteController {

    private final MedicalNoteService medicalNoteService;

    @GetMapping("/notes")
    public List<MedicalNote> getMedicalNotes() {
        return medicalNoteService.getMedicalNotes();
    }

    @GetMapping("/notes/{id}")
    public MedicalNote getMedicalNote(@PathVariable String id) {
        return medicalNoteService.getMedicalNoteById(id);
    }

    @PostMapping("/notes")
    public void addMedicalNote(@RequestBody MedicalNote medicalNote) {
        medicalNoteService.addMedicalNote(medicalNote);
    }

    @DeleteMapping("/notes/{id}")
    public void deleteMedicalNote(@PathVariable String id) {
        medicalNoteService.deleteMedicalNote(id);
    }

}
