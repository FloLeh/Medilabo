package com.openclassrooms.front.config;

import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeignClientConfigTest {

    private FeignClientConfig config;
    private RequestTemplate template;

    @Mock
    private ServletRequestAttributes sra;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    private static final String AUTH_HEADER = "Basic VGVzdFVzZXI6cGFzc3dvcmQ=";

    @BeforeEach
    void setUp() {
        config = new FeignClientConfig();
        template = new RequestTemplate();
    }

    @Test
    void authHeaderInterceptor_shouldAddAuthorizationHeader_whenSessionHasAuthHeader() {
        // GIVEN
        try (MockedStatic<RequestContextHolder> mockedHolder = mockStatic(RequestContextHolder.class)) {

            mockedHolder.when(RequestContextHolder::getRequestAttributes).thenReturn(sra);
            when(sra.getRequest()).thenReturn(request);
            when(request.getSession(false)).thenReturn(session);
            when(session.getAttribute("AUTH_HEADER")).thenReturn(AUTH_HEADER);

            // WHEN
            config.authHeaderInterceptor().apply(template);

            // THEN
            assertNotNull(template.headers().get("Authorization"));
            assertEquals(List.of(AUTH_HEADER), template.headers().get("Authorization"));
        }
    }

    @Test
    void authHeaderInterceptor_shouldNotAddHeader_whenAuthHeaderIsNull() {
        // GIVEN
        try (MockedStatic<RequestContextHolder> mockedHolder = mockStatic(RequestContextHolder.class)) {

            mockedHolder.when(RequestContextHolder::getRequestAttributes).thenReturn(sra);
            when(sra.getRequest()).thenReturn(request);
            when(request.getSession(false)).thenReturn(session);
            when(session.getAttribute("AUTH_HEADER")).thenReturn(null);

            // WHEN
            config.authHeaderInterceptor().apply(template);

            // THEN
            assertEquals(0, template.headers().size());
        }
    }

    @Test
    void authHeaderInterceptor_shouldNotAddHeader_whenSessionIsNull() {
        // GIVEN
        try (MockedStatic<RequestContextHolder> mockedHolder = mockStatic(RequestContextHolder.class)) {

            mockedHolder.when(RequestContextHolder::getRequestAttributes).thenReturn(sra);
            when(sra.getRequest()).thenReturn(request);
            when(request.getSession(false)).thenReturn(null);

            // WHEN
            config.authHeaderInterceptor().apply(template);

            // THEN
            assertEquals(0, template.headers().size());
        }
    }
}