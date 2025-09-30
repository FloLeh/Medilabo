package com.openclassrooms.servicenotes.services;

import com.openclassrooms.servicenotes.domains.MedicalNote;
import com.openclassrooms.servicenotes.repositories.MedicalNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalNoteServiceImpl implements MedicalNoteService {

    private final MedicalNoteRepository medicalNoteRepository;

    public List<MedicalNote> getMedicalNotes() {
        return medicalNoteRepository.findAll();
    }
}
