package com.openclassrooms.servicenotes.controllers;

import com.openclassrooms.servicenotes.domains.MedicalNote;
import com.openclassrooms.servicenotes.services.MedicalNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notes")
public class MedicalNoteController {

    private final MedicalNoteService medicalNoteService;

    @GetMapping
    public List<MedicalNote> getAllMedicalNotes() {
        return medicalNoteService.getAllMedicalNotes();
    }

    @GetMapping("/{id}")
    public List<MedicalNote> getMedicalNotesByPatient(@PathVariable Long id) {
        return medicalNoteService.getMedicalNotesByPatientId(id);
    }

    @PostMapping
    public void addMedicalNote(@RequestBody MedicalNote medicalNote) {
        medicalNoteService.addMedicalNote(medicalNote);
    }

}
