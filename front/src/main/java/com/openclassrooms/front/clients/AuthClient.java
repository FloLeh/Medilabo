package com.openclassrooms.front.clients;

import com.openclassrooms.front.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "api-gateway", url = "${gateway.url}", configuration = FeignClientConfig.class)
public interface AuthClient {
    @GetMapping("/auth/check")
    void checkAuth(@RequestHeader("Authorization") String authHeader);
}
