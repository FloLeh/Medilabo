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
        return "patient/list";
    }

    @GetMapping("/patient/add")
    public String addPatientPage(Model model) {
        model.addAttribute("patient", new Patient(null, "", "", null, "", "", ""));
        return "patient/add";
    }

    @PostMapping("/patient")
    public String addPatient(@ModelAttribute Patient patient) {
        patientService.createPatient(patient);
        return "redirect:/patients";
    }

    @GetMapping("/patient/edit")
    public String updatePatientPage(@RequestParam Long id, Model model) {
        Patient patient = patientService.getPatientById(id);
        model.addAttribute("patient", patient);
        return "patient/edit";
    }

    @PutMapping("/patient")
    public String updatePatient(@ModelAttribute Patient patient, @RequestParam Long id) {
        patientService.updatePatient(patient, id);
        return "redirect:/patients";
    }

    @DeleteMapping("/patient")
    public String deletePatient(@RequestParam Long id) {
        patientService.deletePatient(id);
        return "redirect:/patients";
    }

}
