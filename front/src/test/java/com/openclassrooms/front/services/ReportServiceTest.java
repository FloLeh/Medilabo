package com.openclassrooms.front.services;

import com.openclassrooms.front.clients.ReportClient;
import com.openclassrooms.front.dto.PatientReport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private ReportClient reportClient;

    @InjectMocks
    private ReportServiceImpl reportService;

    private static final Long PATIENT_ID = 1L;
    private static final int RISK_CODE = 2;

    private static final PatientReport MOCK_REPORT = new PatientReport(
            PATIENT_ID,
            RISK_CODE
    );

    private static final List<PatientReport> MOCK_REPORT_LIST = List.of(MOCK_REPORT);

    @Test
    void getPatientReports_shouldReturnListOfReports_whenClientReturnsData() {
        // GIVEN
        when(reportClient.getPatientReports()).thenReturn(MOCK_REPORT_LIST);

        // WHEN
        List<PatientReport> actualReports = reportService.getPatientReports();

        // THEN
        verify(reportClient, times(1)).getPatientReports();
        assertNotNull(actualReports);
        assertEquals(1, actualReports.size());
        assertEquals(PATIENT_ID, actualReports.getFirst().patientId());
        assertEquals(RISK_CODE, actualReports.getFirst().riskCode());
    }

    @Test
    void getPatientReports_shouldReturnEmptyList_whenClientReturnsEmpty() {
        // GIVEN
        when(reportClient.getPatientReports()).thenReturn(Collections.emptyList());

        // WHEN
        List<PatientReport> actualReports = reportService.getPatientReports();

        // THEN
        verify(reportClient, times(1)).getPatientReports();
        assertNotNull(actualReports);
        assertEquals(0, actualReports.size());
    }
}