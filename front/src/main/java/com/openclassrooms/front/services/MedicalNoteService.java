package com.openclassrooms.front.services;

import com.openclassrooms.front.dto.MedicalNote;

import java.util.List;

public interface MedicalNoteService {

    List<MedicalNote> getMedicalNoteByPatientId(Long patientId);

    void saveMedicalNote(MedicalNote medicalNote);

}
