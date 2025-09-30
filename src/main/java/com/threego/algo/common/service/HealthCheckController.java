package com.threego.algo.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    private Environment env;

    public HealthCheckController(Environment env) {
        this.env = env;
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "OK. 포트는 " + env.getProperty("local.server.port");
    }
}

