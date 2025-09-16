package com.openclassrooms.servicepatient.services;

import com.openclassrooms.servicepatient.domains.Patient;
import com.openclassrooms.servicepatient.repositories.PatientRepository;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    private Patient patient;
    private final Calendar calendar = Calendar.getInstance();

    @BeforeEach
    void setUp() {
        calendar.set(1990, Calendar.JANUARY, 1);
        patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setBirthdate(calendar.getTime());
        patient.setGender("M");
    }

    @Test
    void findAll_ShouldReturnPatients() {
        when(patientRepository.findAll()).thenReturn(List.of(patient));

        List<Patient> result = patientService.findAll();

        assertThat(result).hasSize(1).contains(patient);
        verify(patientRepository).findAll();
    }

    @Test
    void findById_ShouldReturnPatient_WhenExists() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        Patient result = patientService.findById(1L);

        assertThat(result).isEqualTo(patient);
        verify(patientRepository).findById(1L);
    }

    @Test
    void findById_ShouldThrowException_WhenNotExists() {
        when(patientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> patientService.findById(99L))
                .isInstanceOf(NoResultException.class);
    }

    @Test
    void save_ShouldSavePatient_WhenValid() {
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        Patient result = patientService.save(patient);

        assertThat(result).isEqualTo(patient);
        verify(patientRepository).save(patient);
    }

    @Test
    void save_ShouldThrowException_WhenPatientIsNull() {
        assertThatThrownBy(() -> patientService.save(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Patient must not be null");
    }

    @Test
    void create_ShouldSavePatient_WhenIdIsNull() {
        Patient newPatient = new Patient();
        newPatient.setFirstName("Jane");
        newPatient.setLastName("Smith");
        newPatient.setBirthdate(calendar.getTime());
        newPatient.setGender("F");

        when(patientRepository.save(any(Patient.class))).thenReturn(newPatient);

        Patient result = patientService.create(newPatient);

        assertThat(result).isEqualTo(newPatient);
        verify(patientRepository).save(newPatient);
    }

    @Test
    void create_ShouldThrowException_WhenIdIsNotNull() {
        assertThatThrownBy(() -> patientService.create(patient))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Patient id must be null");
    }

    @Test
    void update_ShouldUpdatePatient_WhenExists() {
        Patient update = new Patient();
        update.setFirstName("Updated");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        Patient result = patientService.update(update, 1L);

        assertThat(result).isEqualTo(patient);
        verify(patientRepository).save(patient);
    }

    @Test
    void deleteById_ShouldCallRepository() {
        patientService.deleteById(1L);

        verify(patientRepository).deleteById(1L);
    }

}
