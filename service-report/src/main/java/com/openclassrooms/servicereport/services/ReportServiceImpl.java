package com.openclassrooms.servicereport.services;

import com.openclassrooms.servicereport.DTOs.MedicalNote;
import com.openclassrooms.servicereport.DTOs.Patient;
import com.openclassrooms.servicereport.DTOs.PatientReportResponse;
import com.openclassrooms.servicereport.DTOs.PatientWithNotes;
import com.openclassrooms.servicereport.clients.MedicalNoteClient;
import com.openclassrooms.servicereport.clients.PatientClient;
import com.openclassrooms.servicereport.enums.Gender;
import com.openclassrooms.servicereport.enums.RiskLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final PatientClient patientClient;
    private final MedicalNoteClient medicalNoteClient;
    private static final int ageLimit = 30;

    private final static Set<String> triggerWords = Set.of(
            "hémoglobine a1c",
            "microalbumine",
            "taille",
            "poids",
            "fumeur",
            "fumeuse",
            "anormal",
            "cholestérol",
            "vertige",
            "réaction",
            "rechute",
            "anticorps"
    );

    @Override
    public List<PatientWithNotes> getAllPatientsWithNotes() {
        List<Patient> patients = List.copyOf(patientClient.getPatientsList());
        List<MedicalNote> notes = List.copyOf(medicalNoteClient.getNoteList());

        Map<Long, List<String>> notesByPatientId = notes.stream()
                .collect(groupingBy(MedicalNote::patientId, mapping(MedicalNote::note, toList())));

        return patients.stream()
                .map(patient -> new PatientWithNotes(
                        patient.id(),
                        patient.firstName(),
                        patient.lastName(),
                        patient.birthdate(),
                        patient.gender(),
                        patient.address(),
                        patient.phone(),
                        notesByPatientId.getOrDefault(patient.id(), List.of())
                ))
                .toList();
    }

    @Override
    public List<PatientReportResponse> getAllPatientReports() {
        List<PatientWithNotes> patients = getAllPatientsWithNotes();

        return List.copyOf(patients).stream()
                .map(patient -> {
                    RiskLevel riskLevel = calculateRiskLevel(patient);
                    return new PatientReportResponse(
                            patient.id(),
                            riskLevel.code()
                    );
                }).toList();
    }

    private static long countTriggers(final String note) {
        return triggerWords.stream()
                .filter(trigger -> note.toLowerCase().contains(trigger))
                .count();
    }


    private long triggerWordsCountByPatient(final PatientWithNotes patient) {
        return countTriggers(String.join(" ", patient.notes()));
    }

    private RiskLevel calculateRiskLevel(final PatientWithNotes patient) {
        final long triggerCount = triggerWordsCountByPatient(patient);
        final int age = patient.getAge();
        final String gender = patient.gender();

        if (isBorderline(age, triggerCount)) {
            return RiskLevel.BORDERLINE;
        }

        if (isInDanger(age, gender, triggerCount)) {
            return RiskLevel.IN_DANGER;
        }

        if (isEarlyOnset(age, gender, triggerCount)) {
            return RiskLevel.EARLY_ONSET;
        }

        return RiskLevel.NONE;
    }

    private static boolean isEarlyOnset(int age, String gender, long triggerCount) {
        return (age <= ageLimit && gender.equals(Gender.MALE.value())
                    && triggerCount >= 5)
                || (age <= ageLimit && gender.equals(Gender.FEMALE.value())
                    && triggerCount >= 7)
                || (age > ageLimit
                    && triggerCount >= 8);
    }

    private static boolean isInDanger(int age, String gender, long triggerCount) {
        return (age <= ageLimit && gender.equals(Gender.MALE.value())
                    && triggerCount >= 3
                    && triggerCount < 5)
                || (age <= ageLimit && gender.equals(Gender.FEMALE.value())
                    && triggerCount >= 4
                    && triggerCount < 7)
                || (age > ageLimit
                    && triggerCount >= 6
                    && triggerCount < 8);
    }

    private static boolean isBorderline(int age, long triggerCount) {
        return age > ageLimit
                && triggerCount >= 2
                && triggerCount <= 5;
    }
}
