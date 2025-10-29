package com.openclassrooms.front.services;

import com.openclassrooms.front.dto.PatientReport;

import java.util.List;

public interface ReportService {
    List<PatientReport> getPatientReports();
}
