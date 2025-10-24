package com.openclassrooms.front.controllers;

import com.openclassrooms.front.services.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;


    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }


    @PostMapping("/login")
    public String doLogin(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {

        boolean success = authService.authenticate(username, password, session);

        if (success) {
            return "redirect:/patients";
        } else {
            model.addAttribute("error", "Identifiants invalides");
            return "login";
        }
    }
}