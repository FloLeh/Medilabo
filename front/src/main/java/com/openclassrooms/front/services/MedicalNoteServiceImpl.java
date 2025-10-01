package com.openclassrooms.front.services;

import com.openclassrooms.front.clients.MedicalNoteClient;
import com.openclassrooms.front.dto.MedicalNote;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalNoteServiceImpl implements MedicalNoteService {

    private final MedicalNoteClient medicalNoteClient;

    public List<MedicalNote> getMedicalNoteByPatientId(Long patientId) {
        return medicalNoteClient.getMedicalNotesByPatientId(patientId);
    }

    public void saveMedicalNote(MedicalNote medicalNote) {
        medicalNoteClient.saveMedicalNote(medicalNote);
    }

    public void deleteMedicalNote(String id) {
        medicalNoteClient.deleteMedicalNote(id);
    }
}
