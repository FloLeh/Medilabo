package com.openclassrooms.front.services;

import com.openclassrooms.front.clients.ReportClient;
import com.openclassrooms.front.dto.PatientReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportClient reportClient;

    public List<PatientReport> getPatientReports() {
        return reportClient.getPatientReports();
    }

}
