package com.szzzing.api.security.handler;

import com.auth0.jwt.impl.JWTParser;
import com.szzzing.api.dto.user.TokenRedisDto;
import com.szzzing.api.repository.TokenRepository;
import com.szzzing.api.security.jwt.JwtProperties;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

/**
 * 로그아웃 성공 핸들러
 * 1. 리다이렉트 방지
 * 2. redis에 저장된 리프레시 토큰 삭제
 */
@RequiredArgsConstructor
@Slf4j
public class RemoveRedisTokenHandler implements LogoutSuccessHandler {
    private final TokenRepository tokenRepository;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String accessToken = request.getHeader(JwtProperties.HEADER_STRING);
        boolean result = tokenRepository.deleteRefreshToken(accessToken);
        log.info(result + "");

        response.setStatus(200);
    }
}
