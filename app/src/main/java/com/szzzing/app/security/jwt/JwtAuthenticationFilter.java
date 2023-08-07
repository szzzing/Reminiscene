package com.szzzing.app.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.szzzing.app.domain.User;
import com.szzzing.app.repository.UserRepository;
import com.szzzing.app.security.auth.PrincipalDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

// 인증 관련 필터
// 폼로그인을 비활성화했기 때문에 직접 UsernamePasswordAuthenticationFilter를 구현해주어야 함
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private UserRepository userRepository;

    // 인증 요청시 실행되는 함수 -> /login
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // request에 있는 id,pw를 파싱해서 자바 Object로 받기
        ObjectMapper om = new ObjectMapper();
        User user = null;
        try {
            user = om.readValue(request.getInputStream(), User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // usernamepassword 토큰 생성 : 인증을 위해 id, pw 정보를 가지고 있는 토큰
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(user.getId(), user.getPw());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        return authentication;
    }

    // 인증 성공 로직 : Jwt 토큰 생성, 응답 헤더에 담아 전송
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        // 토큰 생성
        String jwtToken = JwtUtil.createToken(principalDetails);
        // 응답 헤더에 토큰 추가
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);
    }
}
