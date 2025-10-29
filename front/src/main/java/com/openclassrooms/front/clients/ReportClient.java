package com.openclassrooms.front.clients;

import com.openclassrooms.front.config.FeignClientConfig;
import com.openclassrooms.front.dto.PatientReport;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "service-report", url = "http://localhost:8080", configuration = FeignClientConfig.class)
public interface ReportClient {

    @GetMapping("/reports")
    List<PatientReport> getPatientReports();

}
