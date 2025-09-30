package com.threego.algo.security;

import com.threego.algo.auth.command.application.service.AuthenticationFilter;
import com.threego.algo.auth.command.application.service.JwtAuthenticationProvider;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class WebSecurity {

    private JwtAuthenticationProvider jwtAuthenticationProvider;
    private Environment env;
    private JwtUtil jwtUtil;


    @Autowired
    public WebSecurity(JwtAuthenticationProvider jwtAuthenticationProvider
            , Environment env
            , JwtUtil jwtUtil) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.env = env;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(jwtAuthenticationProvider));
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
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // 기타 요청
                        .anyRequest().authenticated()
        )
                .sessionManagement(session ->
                    session.sessionCreationPolicy(STATELESS));

        http.addFilter(getAuthenticationFilter(authenticationManager()));

        http.addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private Filter getAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new AuthenticationFilter(authenticationManager, env);
    }

}
