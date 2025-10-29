package com.openclassrooms.servicereport.services;

import com.openclassrooms.servicereport.DTOs.PatientReportResponse;
import com.openclassrooms.servicereport.DTOs.PatientWithNotes;
import com.openclassrooms.servicereport.enums.RiskLevel;

import java.util.List;

public interface ReportService {
    List<PatientWithNotes> getAllPatientsWithNotes();
    long triggerWordsCountByPatient(PatientWithNotes patient);
    RiskLevel calculateRiskLevel(PatientWithNotes patient);
    List<PatientReportResponse> getAllPatientReports();
}
