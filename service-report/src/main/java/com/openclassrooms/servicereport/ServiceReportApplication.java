package com.openclassrooms.servicereport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ServiceReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceReportApplication.class, args);
    }

}
