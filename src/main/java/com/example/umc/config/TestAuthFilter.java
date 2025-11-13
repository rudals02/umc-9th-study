package com.example.umc.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 테스트용 더미 인증 필터
 * 실제 프로덕션에서는 JWT 인증 필터로 교체.
 */
@Component
public class TestAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 테스트용으로 고정 userId 설정 (실제로는 JWT 토큰에서 추출)
        Long testUserId = 1L;
        request.setAttribute("userId", testUserId);

        filterChain.doFilter(request, response);
    }
}