package com.openclassrooms.servicenotes.services;

import com.openclassrooms.servicenotes.domains.MedicalNote;
import com.openclassrooms.servicenotes.repositories.MedicalNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalNoteServiceImpl implements MedicalNoteService {

    private final MedicalNoteRepository medicalNoteRepository;

    public List<MedicalNote> getMedicalNotesByPatientId(Long id) {
        return medicalNoteRepository.findByPatientId(id);
    }

    public void addMedicalNote(MedicalNote medicalNote) {
        Assert.notNull(medicalNote, "MedicalNote must not be null");
        Assert.isNull(medicalNote.getId(), "id must be null");
        Assert.notNull(medicalNote.getPatientId(), "patientId must not be null");
        Assert.notNull(medicalNote.getPatient(), "patient must not be null");
        Assert.notNull( medicalNote.getNote(), "note must not be null");

        medicalNoteRepository.save(medicalNote);
    }

    public void deleteMedicalNote(String id) {
        medicalNoteRepository.deleteById(id);
    }
}
