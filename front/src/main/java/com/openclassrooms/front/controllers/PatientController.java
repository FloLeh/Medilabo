package com.openclassrooms.front.controllers;

import com.openclassrooms.front.dto.Patient;
import com.openclassrooms.front.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/patients")
    public String patientsList(Model model) {
        List<Patient> patients = patientService.getPatientsList();
        model.addAttribute("patients", patients);
        return "patients/list";
    }

    @GetMapping("/patients/add")
    public String addPatientPage(Model model) {
        model.addAttribute("patient", new Patient(null, "", "", null, "", "", ""));
        return "patients/add";
    }

    @PostMapping("/patients")
    public String addPatient(@ModelAttribute Patient patient) {
        patientService.createPatient(patient);
        return "redirect:/patients";
    }

    @GetMapping("/patients/edit/{id}")
    public String updatePatientPage(@PathVariable Long id, Model model) {
        Patient patient = patientService.getPatientById(id);
        model.addAttribute("patient", patient);
        return "patients/edit";
    }

    @PutMapping("/patients/{id}")
    public String updatePatient(@ModelAttribute Patient patient, @PathVariable Long id) {
        patientService.updatePatient(patient, id);
        return "redirect:/patients";
    }

    @DeleteMapping("/patients/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return "redirect:/patients";
    }

}
