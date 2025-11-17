package com.openclassrooms.front.services;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    public boolean authenticate(String username, String password, HttpSession session) {
        String authHeader = buildBasicAuthHeader(username, password);
        session.setAttribute("AUTH_HEADER", authHeader);
        return true;

    }

    private String buildBasicAuthHeader(String username, String password) {
        String token = Base64.getEncoder()
                .encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
        return "Basic " + token;
    }
}
