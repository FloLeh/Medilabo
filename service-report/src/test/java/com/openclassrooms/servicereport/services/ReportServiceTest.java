package com.openclassrooms.servicereport.services;

import com.openclassrooms.servicereport.DTOs.MedicalNote;
import com.openclassrooms.servicereport.DTOs.Patient;
import com.openclassrooms.servicereport.DTOs.PatientReportResponse;
import com.openclassrooms.servicereport.DTOs.PatientWithNotes;
import com.openclassrooms.servicereport.clients.MedicalNoteClient;
import com.openclassrooms.servicereport.clients.PatientClient;
import com.openclassrooms.servicereport.enums.Gender;
import com.openclassrooms.servicereport.enums.RiskLevel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private PatientClient patientClient;

    @Mock
    private MedicalNoteClient medicalNoteClient;

    @InjectMocks
    private ReportServiceImpl reportService;

    private static final long PATIENT_ID = 1L;
    private static final String TRIGGER_WORD_1 = "fumeur";

    private static final List<String> ALL_TRIGGERS = List.of(
            "fumeur", "anormal", "vertige", "rechute", "anticorps",
            "poids", "taille", "cholestérol", "microalbumine", "hémoglobine a1c"
    );

    private final Patient MOCK_PATIENT = new Patient(
            PATIENT_ID, "John", "Doe", LocalDate.now().minusYears(25), Gender.MALE.value(), "1 Main St", "555-1234"
    );

    private final MedicalNote MOCK_NOTE_1 = new MedicalNote(
            "1", PATIENT_ID, MOCK_PATIENT.lastName(), "Note avec " + TRIGGER_WORD_1
    );

    private final MedicalNote MOCK_NOTE_2 = new MedicalNote(
            "2", PATIENT_ID, MOCK_PATIENT.lastName(), "Note sans déclencheur."
    );

    @Test
    void getAllPatientsWithNotes_shouldAggregateDataCorrectly() {
        // GIVEN
        List<Patient> patientList = List.of(MOCK_PATIENT);
        List<MedicalNote> noteList = List.of(MOCK_NOTE_1, MOCK_NOTE_2);

        when(patientClient.getPatientsList()).thenReturn(patientList);
        when(medicalNoteClient.getNoteList()).thenReturn(noteList);

        // WHEN
        List<PatientWithNotes> results = reportService.getAllPatientsWithNotes();

        // THEN
        assertNotNull(results);
        assertEquals(1, results.size());
        PatientWithNotes result = results.getFirst();
        assertEquals(PATIENT_ID, result.id());
        assertEquals(2, result.notes().size());
        assertEquals(MOCK_NOTE_1.note(), result.notes().stream().filter(n -> n.contains(TRIGGER_WORD_1)).findFirst().get());
    }

    @Test
    void getAllPatientsWithNotes_shouldHandlePatientWithoutNotes() {
        // GIVEN
        when(patientClient.getPatientsList()).thenReturn(List.of(MOCK_PATIENT));
        when(medicalNoteClient.getNoteList()).thenReturn(Collections.emptyList());

        // WHEN
        List<PatientWithNotes> results = reportService.getAllPatientsWithNotes();

        // THEN
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(0, results.getFirst().notes().size());
    }

    @Test
    void getAllPatientReports_shouldCalculateRiskLevelAndReturnResponse() {
        // GIVEN
        Patient patient = new Patient(10L, "Test", "User", LocalDate.now().minusYears(50), Gender.FEMALE.value(), "", "");

        when(patientClient.getPatientsList()).thenReturn(List.of(patient));
        when(medicalNoteClient.getNoteList()).thenReturn(List.of(
                new MedicalNote("a", 10L, "Test", "taille"),
                new MedicalNote("b", 10L, "Test", "poids fumeuse")
        ));

        // WHEN
        List<PatientReportResponse> reports = reportService.getAllPatientReports();

        // THEN
        assertNotNull(reports);
        assertEquals(1, reports.size());
        assertEquals(RiskLevel.BORDERLINE.code(), reports.getFirst().riskCode());
    }

    @Test
    void triggerWordsCountByPatient_shouldCountTriggersCorrectly() throws Exception {
        // GIVEN
        String complexNote = "Le patient fumeur a un taux de Cholestérol anormal. Hémoglobine A1C.";
        List<String> notes = List.of(MOCK_NOTE_2.note(), complexNote);

        PatientWithNotes patient = new PatientWithNotes(
                PATIENT_ID, "J", "D", LocalDate.now(), Gender.MALE.value(), "", "", notes
        );

        // WHEN
        long triggerCount = ReflectionTestUtils.invokeMethod(reportService, "triggerWordsCountByPatient", patient);

        // THEN
        assertEquals(4, triggerCount);
    }

    @ParameterizedTest(name = "MALE <= 30, Triggers: {0} -> {1}")
    @CsvSource({
            "2, NONE",
            "3, IN_DANGER",
            "4, IN_DANGER",
            "5, EARLY_ONSET",
            "6, EARLY_ONSET"
    })
    void calculateRiskLevel_shouldDetermineRiskForMaleUnder30(long triggerCount, RiskLevel expectedLevel) {
        // GIVEN
        PatientWithNotes patient = createMockPatient(25, Gender.MALE.value(), triggerCount);

        // WHEN
        RiskLevel actualLevel = ReflectionTestUtils.invokeMethod(reportService, "calculateRiskLevel", patient);

        // THEN
        assertEquals(expectedLevel, actualLevel);
    }

    @ParameterizedTest(name = "FEMALE <= 30, Triggers: {0} -> {1}")
    @CsvSource({
            "3, NONE",
            "4, IN_DANGER",
            "6, IN_DANGER",
            "7, EARLY_ONSET",
            "8, EARLY_ONSET"
    })
    void calculateRiskLevel_shouldDetermineRiskForFemaleUnder30(long triggerCount, RiskLevel expectedLevel) {
        // GIVEN
        PatientWithNotes patient = createMockPatient(25, Gender.FEMALE.value(), triggerCount);

        // WHEN
        RiskLevel actualLevel = ReflectionTestUtils.invokeMethod(reportService, "calculateRiskLevel", patient);

        // THEN
        assertEquals(expectedLevel, actualLevel);
    }

    @ParameterizedTest(name = "Age > 30, Triggers: {0} -> {1}")
    @CsvSource({
            "1, NONE",
            "2, BORDERLINE",
            "5, BORDERLINE",
            "6, IN_DANGER",
            "7, IN_DANGER",
            "8, EARLY_ONSET",
            "9, EARLY_ONSET"
    })
    void calculateRiskLevel_shouldDetermineRiskForPatientsOver30(long triggerCount, RiskLevel expectedLevel) {
        // GIVEN
        PatientWithNotes patient = createMockPatient(50, Gender.MALE.value(), triggerCount);

        // WHEN
        RiskLevel actualLevel = ReflectionTestUtils.invokeMethod(reportService, "calculateRiskLevel", patient);

        // THEN
        assertEquals(expectedLevel, actualLevel);
    }

    private PatientWithNotes createMockPatient(int age, String gender, long triggerCount) {
        if (triggerCount > ALL_TRIGGERS.size()) {
            throw new IllegalArgumentException("triggerCount trop élevé pour le Set de test.");
        }

        List<String> requiredTriggers = ALL_TRIGGERS.subList(0, (int) triggerCount);
        String noteContent = String.join(" ", requiredTriggers);
        List<String> notes = List.of(noteContent);
        LocalDate birthdate = LocalDate.now().minusYears(age);

        return new PatientWithNotes(
                1L, "Mock", "User", birthdate, gender, "addr", "phone", notes
        );
    }
}