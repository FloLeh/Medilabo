package com.openclassrooms.servicenotes.repositories;

import com.openclassrooms.servicenotes.domains.MedicalNote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalNoteRepository extends MongoRepository<MedicalNote,String> {
    List<MedicalNote> findByPatientId(Long patientId);
}
