package com.szzzing.app.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.szzzing.app.domain.User;
import com.szzzing.app.repository.UserRepository;
import com.szzzing.app.security.auth.AuthUtil;
import com.szzzing.app.security.auth.PrincipalDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

// 인가 관련 필터
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("토큰 검증");
        logger.info(request.getRequestURI());

        // 헤더에 토큰을 담아 보냈는지(인증받은 사용자인지) 검사하는 과정
        String token = JwtUtil.getToken(request);

        // 헤더에 토큰이 없는 경우, JwtAuthorizationFilter을 통과하고 JwtAuthenticationFilter에서 로그인을 진행하도록 리턴
        if(token == null || !token.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        // 헤더에 토큰이 있는 경우, 정보 확인
        if(!JwtUtil.validateToken(token)) {
            // 만료된 토큰 - TokenExpiredException 발생
            response.setStatus(901);
        } else {
            // 유효한 토큰 - 아이디를 통해 사용자 존재 여부/권한 확인
            String id = JwtUtil.getId(token);
            if(id != null) {
                User user = userRepository.selectOneById(id);

                // 권한 관리를 위해 SecurityContext에 인증 정보 저장
                PrincipalDetails principalDetails = new PrincipalDetails(user);
                Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails.getUser().getId(), null, principalDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // 응답 헤더에 토큰과 유저 정보 추가
                response.setHeader(JwtProperties.HEADER_STRING, token);
                AuthUtil.setUserHeader(response, user);
            }
        }
        chain.doFilter(request, response);
    }
}