package com.openclassrooms.servicenotes.services;

import com.openclassrooms.servicenotes.domains.MedicalNote;

import java.util.List;

public interface MedicalNoteService {
    List<MedicalNote> getMedicalNotesByPatientId(Long id);
    void addMedicalNote(MedicalNote medicalNote);
}
