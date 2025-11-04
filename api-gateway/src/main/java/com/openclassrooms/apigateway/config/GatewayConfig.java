package com.openclassrooms.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;


@Configuration
public class GatewayConfig {

    @Value("${service.patients.url}")
    private String patientsUrl;

    @Value("${service.notes.url}")
    private String notesUrl;

    @Value("${service.reports.url}")
    private String reportsUrl;

    @Bean
    public RouterFunction<ServerResponse> routePatient() {
        return route()
                .path("/patients", builder -> builder
                        .route(request -> true, http())
                )
                .before(uri(patientsUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> routeNotes() {
        return route()
                .path("/notes", builder -> builder
                        .route(request -> true, http())
                )
                .before(uri(notesUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> routeReports() {
        return route()
                .path("/reports", builder -> builder
                        .route(request -> true, http())
                )
                .before(uri(reportsUrl))
                .build();
    }
}
