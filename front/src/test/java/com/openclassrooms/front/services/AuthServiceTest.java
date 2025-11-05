package com.openclassrooms.front.services;

import com.openclassrooms.front.clients.AuthClient;
import feign.FeignException;
import feign.Request;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthClient authClient;

    @Mock
    private HttpSession session;

    @InjectMocks
    private AuthServiceImpl authService;

    private static final String USERNAME = "testUser";
    private static final String PASSWORD = "password123";
    private String expectedAuthHeader;

    @BeforeEach
    void setUp() {
        String token = Base64.getEncoder()
                .encodeToString((USERNAME + ":" + PASSWORD).getBytes(StandardCharsets.UTF_8));
        expectedAuthHeader = "Basic " + token;
    }


    @Test
    void authenticate_shouldReturnTrueAndSetSessionAttribute_whenAuthIsSuccessful() {
        // GIVEN
        doNothing().when(authClient).checkAuth(expectedAuthHeader);

        // WHEN
        boolean result = authService.authenticate(USERNAME, PASSWORD, session);

        // THEN
        assertTrue(result, "L'authentification devrait réussir.");
        verify(authClient, times(1)).checkAuth(expectedAuthHeader);
        verify(session, times(1)).setAttribute("AUTH_HEADER", expectedAuthHeader);
    }


    @Test
    void authenticate_shouldReturnFalseAndNotSetSessionAttribute_whenAuthFailsWithUnauthorized() {
        // GIVEN
        Request mockedRequest = mock(Request.class);

        FeignException.Unauthorized unauthorizedException = new FeignException.Unauthorized(
                "401 Unauthorized",
                mockedRequest,
                null,
                null
        );
        doThrow(unauthorizedException).when(authClient).checkAuth(expectedAuthHeader);

        // WHEN
        boolean result = authService.authenticate(USERNAME, PASSWORD, session);

        // THEN
        assertFalse(result, "L'authentification devrait échouer.");
        verify(session, never()).setAttribute(eq("AUTH_HEADER"), anyString());
    }

    @Test
    void authenticate_shouldThrowException_whenOtherFeignExceptionOccurs() {
        // GIVEN
        Request mockedRequest = mock(Request.class);

        FeignException.Forbidden forbiddenException = new FeignException.Forbidden(
                "403 Forbidden",
                mockedRequest,
                null,
                null
        );
        doThrow(forbiddenException).when(authClient).checkAuth(anyString());

        // WHEN / THEN
        org.junit.jupiter.api.Assertions.assertThrows(FeignException.class, () -> authService.authenticate(USERNAME, PASSWORD, session));
        verify(session, never()).setAttribute(eq("AUTH_HEADER"), anyString());
    }
}