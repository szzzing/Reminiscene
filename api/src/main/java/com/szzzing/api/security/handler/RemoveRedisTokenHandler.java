package com.szzzing.api.security.handler;

import com.szzzing.api.dto.user.TokenRedisDto;
import com.szzzing.api.repository.TokenRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

/**
 * redis에 저장된 토큰 삭제
 */
@RequiredArgsConstructor
@Slf4j
public class RemoveRedisTokenHandler implements LogoutSuccessHandler {
    private final TokenRepository tokenRepository;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String id = request.getRequestURI().toString().split("/")[2];
        boolean result = tokenRepository.delete(new TokenRedisDto(id, null, "A"));
        log.info(result+"");
        if(result) {
            response.setStatus(200);
        } else {
            response.setStatus(401);
        }
    }
}
