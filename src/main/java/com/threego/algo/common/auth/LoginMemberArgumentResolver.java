package com.threego.algo.common.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Collections;
import java.util.Enumeration;

@Component
@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (!parameter.hasParameterAnnotation(LoginMember.class)) return false;

        Class<?> type = parameter.getParameterType();
        return type.equals(Integer.class) || type.equals(int.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        // 모든 헤더 출력
        log.info("=== 요청 헤더 확인 ===");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            log.info("Header: {} = {}", headerName, request.getHeader(headerName));
        }

        String memberIdHeader = request.getHeader("X-Member-Id");
        log.info("X-Member-Id 헤더 값: {}", memberIdHeader);

        if (memberIdHeader == null) {
            throw new IllegalArgumentException("인증 정보가 존재하지 않습니다. (X-Member-Id 누락)");
        }
        try {
            return Integer.parseInt(memberIdHeader);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("유효하지 않은 사용자 ID 형식입니다: " + memberIdHeader);
        }

    }
}
