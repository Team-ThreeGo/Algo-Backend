package com.threego.algo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        HttpServletRequest wrappedRequest = request;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            if(jwtUtil.validateToken(token)) {
                Authentication authentication = jwtUtil.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // JWT 토큰에서 member-id를 추출하여 헤더에 추가
                Integer memberId = jwtUtil.getMemberIdFromToken(token);
                if(memberId != null) {
                    wrappedRequest = new MemberIdRequestWrapper(request, memberId);
                }
            }
        }

        filterChain.doFilter(wrappedRequest, response);

    }

    // Member-Id를 헤더에 추가하는 RequestWrapper
    private static class MemberIdRequestWrapper extends HttpServletRequestWrapper {
        private final int memberId;

        public MemberIdRequestWrapper(HttpServletRequest request, int memberId) {
            super(request);
            this.memberId = memberId;
        }

        @Override
        public String getHeader(String name) {
            if ("Member-Id".equalsIgnoreCase(name)) {
                return String.valueOf(memberId);
            }
            return super.getHeader(name);
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            if ("Member-Id".equalsIgnoreCase(name)) {
                return Collections.enumeration(Collections.singletonList(String.valueOf(memberId)));
            }
            return super.getHeaders(name);
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            List<String> names = Collections.list(super.getHeaderNames());
            if (!names.stream().anyMatch(name -> "Member-Id".equalsIgnoreCase(name))) {
                names.add("Member-Id");
            }
            return Collections.enumeration(names);
        }
    }

}




