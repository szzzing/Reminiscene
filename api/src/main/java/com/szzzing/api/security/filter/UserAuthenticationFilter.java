package com.szzzing.api.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.szzzing.api.dto.user.UserDto;
import com.szzzing.api.repository.UserRepository;
import com.szzzing.api.security.auth.AuthUtil;
import com.szzzing.api.security.auth.PrincipalDetails;
import com.szzzing.api.security.jwt.JwtProperties;
import com.szzzing.api.security.jwt.JwtUtil;
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

// 인증 관련 필터
// 폼로그인을 비활성화했기 때문에 직접 UsernamePasswordAuthenticationFilter를 구현해주어야 함
@RequiredArgsConstructor
public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    // 인증 요청시 실행되는 함수 -> /login
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // request에 있는 id,pw를 파싱해서 자바 Object로 받기
        ObjectMapper om = new ObjectMapper();
        UserDto authInfo = null;
        try {
            authInfo = om.readValue(request.getInputStream(), UserDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // usernamepassword 토큰 생성 : 인증을 위해 id, pw 정보를 가지고 있는 토큰
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(authInfo.getId(), authInfo.getPw());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        return authentication;
    }

    // 인증 성공 로직 : Jwt 토큰 생성, 응답 헤더에 담아 전송
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        logger.info("로그인 성공 : "+principalDetails);

        // 토큰 생성
        String jwtToken = JwtUtil.createToken(principalDetails);
        // 응답 헤더에 토큰 추가
        response.addHeader(JwtProperties.HEADER_STRING, jwtToken);

        // 응답 헤더에 사용자 정보 추가
        UserDto userDto = userRepository.selectOneUser(principalDetails.getUserDto().getId());
        response.setHeader("user", AuthUtil.userToJson(userDto));
    }

    // 인증 실패 로직
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);
    }
}