package com.openclassrooms.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.AfterFilterFunctions.addResponseHeader;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;


@Configuration
public class GatewayConfig {

    @Bean
    public RouterFunction<ServerResponse> routePatients() {
        return route("patients")
                .GET("/patients", http())
                .before(uri("http://localhost:8081"))
                .after(addResponseHeader("X-Route", "patients"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> routePatient() {
        return route("patient")
                .GET("/patient", http())
                .before(uri("http://localhost:8081"))
                .after(addResponseHeader("X-Route", "patient"))
                .build();
    }
}