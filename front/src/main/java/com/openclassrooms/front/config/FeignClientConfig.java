package com.openclassrooms.front.config;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor authHeaderInterceptor() {
        return template -> {
            RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
            if (attrs instanceof ServletRequestAttributes sra) {
                HttpServletRequest request = sra.getRequest();
                HttpSession session = request.getSession(false);
                if (session != null) {
                    String authHeader = (String) session.getAttribute("AUTH_HEADER");
                    if (authHeader != null) {
                        template.header("Authorization", authHeader);
                    }
                }
            }
        };
    }
}