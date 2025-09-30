package com.openclassrooms.servicenotes.repositories;

import com.openclassrooms.servicenotes.domains.MedicalNote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalNoteRepository extends MongoRepository<MedicalNote,String> {
}
