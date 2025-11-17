package com.openclassrooms.front.controllers;

import com.openclassrooms.front.dto.Patient;
import com.openclassrooms.front.dto.PatientReport;
import com.openclassrooms.front.dto.PatientWithReportCodeAndLabel;
import com.openclassrooms.front.enums.Gender;
import com.openclassrooms.front.enums.RiskLevel;
import com.openclassrooms.front.services.PatientService;
import com.openclassrooms.front.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;
    private final ReportService reportService;

    @GetMapping
    public String patientsList(Model model) {
        List<Patient> patients = patientService.getPatientsList();
        List<PatientReport> reports = reportService.getPatientReports();

        Map<Long, Integer> riskByPatientId = reports.stream()
                .collect(Collectors.toMap(PatientReport::patientId, PatientReport::riskCode));

        List<PatientWithReportCodeAndLabel> patientsWithReportLabels = patients.stream()
                .map(patient -> new PatientWithReportCodeAndLabel(patient,
                        riskByPatientId.get(patient.id()),
                        RiskLevel.fromCode(riskByPatientId.get(patient.id())).getLabel()
                ))
                .toList();


        model.addAttribute("patients", patientsWithReportLabels);
        return "patients/list";
    }

    @GetMapping("/add")
    public String addPatientPage(Model model) {
        model.addAttribute("patient", new Patient(null, "", "", null, Gender.F, "", ""));
        return "patients/add";
    }

    @PostMapping
    public String addPatient(@ModelAttribute Patient patient) {
        patientService.createPatient(patient);
        return "redirect:/patients";
    }

    @GetMapping("/edit/{id}")
    public String updatePatientPage(@PathVariable Long id, Model model) {
        Patient patient = patientService.getPatientById(id);
        model.addAttribute("patient", patient);
        return "patients/edit";
    }

    @PutMapping("/{id}")
    public String updatePatient(@ModelAttribute Patient patient, @PathVariable Long id) {
        patientService.updatePatient(patient, id);
        return "redirect:/patients";
    }

}
