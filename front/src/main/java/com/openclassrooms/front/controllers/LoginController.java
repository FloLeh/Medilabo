package com.openclassrooms.front.controllers;

import com.openclassrooms.front.services.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
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
    public String doLogin(@RequestParam String username, @RequestParam String password, HttpSession session) {

        authService.authenticate(username, password, session);

        return "redirect:/patients";
    }
}
