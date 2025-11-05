package com.openclassrooms.servicereport.controllers;

import com.openclassrooms.servicereport.DTOs.PatientReportResponse;
import com.openclassrooms.servicereport.services.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
class ReportControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReportService reportService;

    private final PatientReportResponse MOCK_REPORT_1 = new PatientReportResponse(1L, 1);
    private final PatientReportResponse MOCK_REPORT_2 = new PatientReportResponse(2L, 2);
    private final List<PatientReportResponse> MOCK_REPORT_LIST = List.of(MOCK_REPORT_1, MOCK_REPORT_2);

    // ------------------------------------------------------------------------------------------------

    @Test
    void getReports_shouldReturnAllReportsFromService() throws Exception {
        // GIVEN
        when(reportService.getAllPatientReports()).thenReturn(MOCK_REPORT_LIST);

        // WHEN / THEN
        mockMvc.perform(get("/reports")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].patientId").value(1L))
                .andExpect(jsonPath("$[0].riskCode").value(1));
    }

    @Test
    void getReports_shouldReturnEmptyList_whenServiceReturnsEmpty() throws Exception {
        // GIVEN
        when(reportService.getAllPatientReports()).thenReturn(List.of());

        // WHEN / THEN
        mockMvc.perform(get("/reports")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));
    }

}