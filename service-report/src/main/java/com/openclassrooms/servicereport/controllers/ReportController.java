package com.openclassrooms.servicereport.controllers;

import com.openclassrooms.servicereport.DTOs.PatientReportResponse;
import com.openclassrooms.servicereport.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/reports")
    public List<PatientReportResponse> getReports() {
        return reportService.getAllPatientReports();
    }

}
