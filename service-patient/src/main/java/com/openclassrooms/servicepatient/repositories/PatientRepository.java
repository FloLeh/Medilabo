package com.openclassrooms.servicepatient.repositories;

import com.openclassrooms.servicepatient.domains.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
