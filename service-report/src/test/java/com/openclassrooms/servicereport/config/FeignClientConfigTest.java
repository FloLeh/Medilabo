package com.openclassrooms.servicereport.config;

import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeignClientConfigTest {

    private FeignClientConfig feignClientConfig;

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private RequestTemplate mockTemplate;

    private static final String AUTH_HEADER_VALUE = "Bearer test_token_12345";
    private static final String AUTH_HEADER_NAME = "Authorization";

    @BeforeEach
    void setUp() {
        feignClientConfig = new FeignClientConfig();
        ServletRequestAttributes attributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(attributes);
    }

    @AfterEach
    void tearDown() {
        RequestContextHolder.resetRequestAttributes();
    }

    // ------------------------------------------------------------------------------------------------

    @Test
    void forwardAuthHeaderInterceptor_shouldForwardHeader_whenAuthorizationHeaderIsPresent() {
        // GIVEN
        when(mockRequest.getHeader(AUTH_HEADER_NAME)).thenReturn(AUTH_HEADER_VALUE);

        // WHEN
        feignClientConfig.forwardAuthHeaderInterceptor().apply(mockTemplate);

        // THEN
        verify(mockTemplate).header(AUTH_HEADER_NAME, AUTH_HEADER_VALUE);
    }

    @Test
    void forwardAuthHeaderInterceptor_shouldNotForwardHeader_whenAuthorizationHeaderIsMissing() {
        // GIVEN
        // Simuler l'absence de l'entête Authorization
        when(mockRequest.getHeader(AUTH_HEADER_NAME)).thenReturn(null);

        // WHEN
        feignClientConfig.forwardAuthHeaderInterceptor().apply(mockTemplate);

        // THEN
        verify(mockTemplate, never()).header(AUTH_HEADER_NAME, AUTH_HEADER_VALUE);
    }

    @Test
    void forwardAuthHeaderInterceptor_shouldDoNothing_whenRequestContextIsNotAvailable() {
        // GIVEN
        RequestContextHolder.resetRequestAttributes();

        // WHEN
        feignClientConfig.forwardAuthHeaderInterceptor().apply(mockTemplate);

        // THEN
        verify(mockRequest, never()).getHeader(AUTH_HEADER_NAME);
        verify(mockTemplate, never()).header(AUTH_HEADER_NAME, AUTH_HEADER_VALUE);
    }
}