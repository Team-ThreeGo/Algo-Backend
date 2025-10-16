package com.threego.algo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class WebSecurity {

    private Environment env;

    @Autowired
    public WebSecurity(Environment env) {
        this.env = env;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(authz ->
                authz
                        // Swagger 허용
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // Auth (회원가입, 로그인, 메일 인증) 허용
                        .requestMatchers("/auth/**", "/health/**", "/signup/**", "/login/**").permitAll()

                        // 관리자 API
//                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // 기타 요청
                        .anyRequest().permitAll()
        )
                .sessionManagement(session ->
                    session.sessionCreationPolicy(STATELESS));

        return http.build();
    }

}
