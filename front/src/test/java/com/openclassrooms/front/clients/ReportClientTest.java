package com.openclassrooms.front.clients;

import com.openclassrooms.front.dto.PatientReport;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportClientTest {

    @Mock
    private ReportClient reportClient;

    private static final Long PATIENT_ID = 1L;
    private static final int RISK_CODE = 3;

    private static final PatientReport MOCK_REPORT = new PatientReport(
            PATIENT_ID,
            RISK_CODE
    );

    private static final List<PatientReport> MOCK_REPORT_LIST = List.of(MOCK_REPORT);

    @Test
    void getPatientReports_shouldReturnListOfReports_whenSuccessful() {
        // GIVEN
        when(reportClient.getPatientReports()).thenReturn(MOCK_REPORT_LIST);

        // WHEN
        List<PatientReport> actualReports = reportClient.getPatientReports();

        // THEN
        verify(reportClient, times(1)).getPatientReports();
        assertNotNull(actualReports);
        assertEquals(1, actualReports.size());
        assertEquals(RISK_CODE, actualReports.getFirst().riskCode());
    }

    @Test
    void getPatientReports_shouldReturnEmptyList_whenNoReportsExist() {
        // GIVEN
        when(reportClient.getPatientReports()).thenReturn(Collections.emptyList());

        // WHEN
        List<PatientReport> actualReports = reportClient.getPatientReports();

        // THEN
        verify(reportClient, times(1)).getPatientReports();
        assertNotNull(actualReports);
        assertEquals(0, actualReports.size());
    }

    @Test
    void getPatientReports_shouldThrowException_whenFeignCallFails() {
        // GIVEN
        doThrow(FeignException.ServiceUnavailable.class).when(reportClient).getPatientReports();

        // WHEN / THEN
        assertThrows(FeignException.ServiceUnavailable.class, () -> reportClient.getPatientReports());
        verify(reportClient, times(1)).getPatientReports();
    }
}