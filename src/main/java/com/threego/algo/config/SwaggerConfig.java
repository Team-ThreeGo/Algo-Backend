package com.threego.algo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

/* 설명. http://localhost:8080/swagger-ui/index.html */
@OpenAPIDefinition(
        info = @Info(
                title = "Algo - API 명세서",
                version = "v1.0.0"
        )
)
@Configuration
public class SwaggerConfig {
}