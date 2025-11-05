package com.openclassrooms.front.controllers;

import com.openclassrooms.front.services.AuthService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    private static final String VALID_USERNAME = "user";
    private static final String VALID_PASSWORD = "password";
    private static final String ERROR_MESSAGE = "Identifiants invalides";

    @Test
    void loginForm_shouldReturnLoginPage() throws Exception {
        // GIVEN / WHEN / THEN
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void doLogin_shouldRedirectToPatients_whenAuthenticationIsSuccessful() throws Exception {
        // GIVEN
        when(authService.authenticate(anyString(), anyString(), any(HttpSession.class))).thenReturn(true);

        // WHEN / THEN
        mockMvc.perform(post("/login")
                        .param("username", VALID_USERNAME)
                        .param("password", VALID_PASSWORD))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/patients"))
                .andExpect(model().attributeDoesNotExist("error"));
    }

    @Test
    void doLogin_shouldReturnToLoginPageWithError_whenAuthenticationFails() throws Exception {
        // GIVEN
        when(authService.authenticate(anyString(), anyString(), any(HttpSession.class))).thenReturn(false);

        // WHEN / THEN
        mockMvc.perform(post("/login")
                        .param("username", "invalid")
                        .param("password", "fail"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", ERROR_MESSAGE));
    }
}