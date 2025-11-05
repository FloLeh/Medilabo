package com.openclassrooms.front.clients;

import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthClientTest {

    @Mock
    private AuthClient authClient;

    private static final String VALID_AUTH_HEADER = "Basic VGVzdFVzZXI6cGFzc3dvcmQ=";
    private static final String INVALID_AUTH_HEADER = "Basic aW52YWxpZDp0b2tlbg==";

    @Test
    void checkAuth_shouldExecuteSuccessfully_whenAuthHeaderIsValid() {
        // GIVEN
        doNothing().when(authClient).checkAuth(VALID_AUTH_HEADER);

        // WHEN / THEN
        assertDoesNotThrow(() -> authClient.checkAuth(VALID_AUTH_HEADER));
        verify(authClient, times(1)).checkAuth(VALID_AUTH_HEADER);
    }

    @Test
    void checkAuth_shouldThrowUnauthorizedException_whenAuthHeaderIsInvalid() {
        // GIVEN
        Request mockedRequest = Request.create(
                Request.HttpMethod.GET,
                "/auth/check",
                new HashMap<>(),
                null,
                null,
                null
        );

        FeignException.Unauthorized unauthorizedException = new FeignException.Unauthorized(
                "401 Unauthorized",
                mockedRequest,
                null,
                Collections.emptyMap()
        );

        doThrow(unauthorizedException).when(authClient).checkAuth(INVALID_AUTH_HEADER);

        // WHEN / THEN
        assertThrows(FeignException.Unauthorized.class, () -> authClient.checkAuth(INVALID_AUTH_HEADER));
        verify(authClient, times(1)).checkAuth(INVALID_AUTH_HEADER);
    }
}