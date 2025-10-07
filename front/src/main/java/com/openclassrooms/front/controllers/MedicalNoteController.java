package com.openclassrooms.front.controllers;

import com.openclassrooms.front.dto.MedicalNote;
import com.openclassrooms.front.dto.Patient;
import com.openclassrooms.front.services.MedicalNoteService;
import com.openclassrooms.front.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/patients")
public class MedicalNoteController {

    private final MedicalNoteService medicalNoteService;
    private final PatientService patientService;

    @GetMapping("/{patientId}/notes")
    public String showMedicalNote(@PathVariable Long patientId, Model model) {
        List<MedicalNote> notes = medicalNoteService.getMedicalNoteByPatientId(patientId);
        Patient patient = patientService.getPatientById(patientId);
        model.addAttribute("medicalNotes", notes);
        model.addAttribute("patient", patient);
        return "/patients/notes";
    }

    @PostMapping("/{patientId}/notes")
    public String saveMedicalNote(@ModelAttribute MedicalNote medicalNote, @PathVariable Long patientId) {
        medicalNoteService.saveMedicalNote(medicalNote);
        return "redirect:/patients/" + patientId + "/notes";
    }

}
