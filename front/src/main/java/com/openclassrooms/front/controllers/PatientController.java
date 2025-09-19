package com.openclassrooms.front.controllers;

import com.openclassrooms.front.dto.Patient;
import com.openclassrooms.front.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @DeleteMapping("/patient")
    public String deletePatient(@RequestParam Long id) {
        patientService.deletePatient(id);
        return "redirect:/patients";
    }

}
