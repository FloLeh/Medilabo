package com.openclassrooms.servicenotes.services;

import com.openclassrooms.servicenotes.domains.MedicalNote;

import java.util.List;

public interface MedicalNoteService {
    List<MedicalNote> getMedicalNotes();
    MedicalNote getMedicalNoteById(String id);
    void addMedicalNote(MedicalNote medicalNote);
    void deleteMedicalNote(String id);
}
