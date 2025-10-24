package com.openclassrooms.front.services;

import com.openclassrooms.front.clients.AuthClient;
import feign.FeignException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthClient authClient;

    public boolean authenticate(String username, String password, HttpSession session) {
        String authHeader = buildBasicAuthHeader(username, password);

        try {
            authClient.checkAuth(authHeader);
            session.setAttribute("AUTH_HEADER", authHeader);
            return true;
        } catch (FeignException.Unauthorized e) {
            return false;
        }
    }

    private String buildBasicAuthHeader(String username, String password) {
        String token = Base64.getEncoder()
                .encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
        return "Basic " + token;
    }
}
