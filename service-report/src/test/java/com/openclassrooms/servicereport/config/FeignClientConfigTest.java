package com.openclassrooms.servicereport.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.assertThat;

class FeignClientConfigTest {

    private RequestInterceptor interceptor;

    @BeforeEach
    void setUp() {
        interceptor = new FeignClientConfig().forwardAuthHeaderInterceptor();
    }

    @Test
    void shouldAddAuthorizationHeaderFromCurrentRequest() {
        // given
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addHeader("Authorization", "Basic dXNlcjpwYXNz");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));

        RequestTemplate template = new RequestTemplate();

        // when
        interceptor.apply(template);

        // then
        assertThat(template.headers())
                .containsKey("Authorization");
        assertThat(template.headers().get("Authorization"))
                .containsExactly("Basic dXNlcjpwYXNz");

        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void shouldNotAddHeaderIfNoRequestAttributes() {
        // given
        RequestContextHolder.resetRequestAttributes();
        RequestTemplate template = new RequestTemplate();

        // when
        interceptor.apply(template);

        // then
        assertThat(template.headers()).doesNotContainKey("Authorization");
    }

    @Test
    void shouldNotAddHeaderIfNoAuthorizationHeaderPresent() {
        // given
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));
        RequestTemplate template = new RequestTemplate();

        // when
        interceptor.apply(template);

        // then
        assertThat(template.headers()).doesNotContainKey("Authorization");

        RequestContextHolder.resetRequestAttributes();
    }
}
