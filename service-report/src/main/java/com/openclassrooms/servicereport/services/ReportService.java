package com.openclassrooms.servicereport.services;

import com.openclassrooms.servicereport.DTOs.PatientReportResponse;
import com.openclassrooms.servicereport.DTOs.PatientWithNotes;

import java.util.List;

public interface ReportService {
    List<PatientWithNotes> getAllPatientsWithNotes();
    List<PatientReportResponse> getAllPatientReports();
}
