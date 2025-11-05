package com.openclassrooms.front.controllers;

import com.openclassrooms.front.dto.Patient;
import com.openclassrooms.front.dto.PatientReport;
import com.openclassrooms.front.services.PatientService;
import com.openclassrooms.front.services.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PatientService patientService;

    @MockitoBean
    private ReportService reportService;

    private static final Long PATIENT_ID = 1L;
    private static final int RISK_CODE = 2;

    private static final Patient MOCK_PATIENT = new Patient(
            PATIENT_ID, "John", "Doe", LocalDate.of(1980, 1, 1), "M", "123 Main St", "555-1234"
    );
    private static final List<Patient> MOCK_PATIENT_LIST = List.of(MOCK_PATIENT);

    private static final PatientReport MOCK_REPORT = new PatientReport(PATIENT_ID, RISK_CODE);
    private static final List<PatientReport> MOCK_REPORT_LIST = List.of(MOCK_REPORT);

    @Test
    void patientsList_shouldDisplayPatientsWithReportLabels() throws Exception {
        // GIVEN
        when(patientService.getPatientsList()).thenReturn(MOCK_PATIENT_LIST);
        when(reportService.getPatientReports()).thenReturn(MOCK_REPORT_LIST);

        // WHEN / THEN
        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/list"))
                .andExpect(model().attributeExists("patients"))
                .andExpect(model().attribute("patients", hasSize(1)));
    }

    @Test
    void addPatientPage_shouldReturnAddView() throws Exception {
        // GIVEN / WHEN / THEN
        mockMvc.perform(get("/patients/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/add"))
                .andExpect(model().attributeExists("patient"));
    }

    @Test
    void addPatient_shouldCreatePatientAndRedirect() throws Exception {
        // GIVEN
        doNothing().when(patientService).createPatient(any(Patient.class));

        // WHEN
        mockMvc.perform(post("/patients")
                        .param("firstName", "Jane")
                        .param("lastName", "Smith"))

                // THEN
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/patients"));

        verify(patientService).createPatient(any(Patient.class));
    }

    @Test
    void updatePatientPage_shouldReturnEditViewWithPatient() throws Exception {
        // GIVEN
        when(patientService.getPatientById(PATIENT_ID)).thenReturn(MOCK_PATIENT);

        // WHEN / THEN
        mockMvc.perform(get("/patients/edit/{id}", PATIENT_ID))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/edit"))
                .andExpect(model().attribute("patient", MOCK_PATIENT));
    }

    @Test
    void updatePatient_shouldUpdatePatientAndRedirect() throws Exception {
        // GIVEN
        doNothing().when(patientService).updatePatient(any(Patient.class), eq(PATIENT_ID));

        // WHEN
        mockMvc.perform(put("/patients/{id}", PATIENT_ID)
                        .param("firstName", "John")
                        .param("lastName", "Updated"))

                // THEN
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/patients"));

        verify(patientService).updatePatient(any(Patient.class), eq(PATIENT_ID));
    }
}