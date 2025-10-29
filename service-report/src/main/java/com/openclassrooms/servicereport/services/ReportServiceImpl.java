package com.openclassrooms.servicereport.services;

import com.openclassrooms.servicereport.DTOs.MedicalNote;
import com.openclassrooms.servicereport.DTOs.Patient;
import com.openclassrooms.servicereport.DTOs.PatientReportResponse;
import com.openclassrooms.servicereport.DTOs.PatientWithNotes;
import com.openclassrooms.servicereport.clients.MedicalNoteClient;
import com.openclassrooms.servicereport.clients.PatientClient;
import com.openclassrooms.servicereport.enums.RiskLevel;
import com.openclassrooms.servicereport.enums.TriggerWords;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final PatientClient patientClient;
    private final MedicalNoteClient medicalNoteClient;

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

    public long triggerWordsCountByPatient(PatientWithNotes patient) {
        return TriggerWords.countTriggers(String.join(" ", patient.notes()));
    }

    public RiskLevel calculateRiskLevel(PatientWithNotes patient) {
        long triggerCount = triggerWordsCountByPatient(patient);
        int age = patient.getAge();
        String gender = patient.gender();

        if (triggerCount == 0) {
            return RiskLevel.NONE;
        }

        if (age > 30 && triggerCount >= 2 && triggerCount <= 5) {
            return RiskLevel.BORDERLINE;
        }

        if ((age < 30 && gender.equals("M") && triggerCount >= 3 && triggerCount < 5)
                || (age < 30 && gender.equals("F") && triggerCount >= 4 && triggerCount < 7)
                || (age > 30 && triggerCount >= 6 && triggerCount <= 7)) {
            return RiskLevel.IN_DANGER;
        }

        if ((age < 30 && gender.equals("M") && triggerCount >= 5)
                || (age < 30 && gender.equals("F") && triggerCount >= 7)
                || (age > 30 && triggerCount >= 8)) {
            return RiskLevel.EARLY_ONSET;
        }

        return RiskLevel.NONE;
    }

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
}
