package com.threego.algo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Algo - API 명세서",
                version = "v1.0.0"
        )
)
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Authentication",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer").bearerFormat("JWT"))
                );
    }

    /* 전체 API */
    @Bean
    public GroupedOpenApi allApi() {
        return GroupedOpenApi.builder()
                .group("All APIs")
                .pathsToMatch("/**")
                .build();
    }

    /* 설명. 인증/인가 */
    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("Auth API")
                .pathsToMatch("/auth/**", "/login", "/mail/**")
                .addOpenApiCustomizer(openApi -> openApi.getSecurity().clear())
                .build();
    }

    /* 설명. 알고리즘 */
    @Bean
    public GroupedOpenApi algorithmApi() {
        return GroupedOpenApi.builder()
                .group("Algorithm API")
                .pathsToMatch("/algo/**", "/admin/algo/**")
                .build();
    }

    /* 설명. 코딩 (풀이 + 문제) */
    @Bean
    public GroupedOpenApi codingApi() {
        return GroupedOpenApi.builder()
                .group("Coding API")
                .pathsToMatch("/coding/**", "/admin/coding/**", "/coding-problem/**", "/admin/coding-problem/**")
                .build();
    }

    /* 설명. Career Info */
    @Bean
    public GroupedOpenApi careerInfoApi() {
        return GroupedOpenApi.builder()
                .group("Career Info API")
                .pathsToMatch("/career-info/**", "/admin/career-info/**")
                .build();
    }

    /* Member */
    @Bean
    public GroupedOpenApi memberApi() {
        return GroupedOpenApi.builder()
                .group("Member API")
                .pathsToMatch("/member/**", "/admin/member/**")
                .build();
    }

    /* Study */
    @Bean
    public GroupedOpenApi studyApi() {
        return GroupedOpenApi.builder()
                .group("Study API")
                .pathsToMatch("/study/**", "/admin/study/**")
                .build();
    }

    /* Study Recruit */
    @Bean
    public GroupedOpenApi studyRecruitApi() {
        return GroupedOpenApi.builder()
                .group("Study Recruit API")
                .pathsToMatch("/study-recruit/**", "/admin/study-recruit/**")
                .build();
    }
}
