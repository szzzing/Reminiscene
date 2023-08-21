package com.szzzing.api.security.filter;

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
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class UserAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    public UserAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("토큰 검증 - "+request.getRequestURI());

        // 헤더에 토큰을 담아 보냈는지(인증받은 사용자인지) 검사하는 과정
        String token = JwtUtil.getToken(request);

        // 헤더에 토큰이 없는 경우
        if(token == null || !token.startsWith(JwtProperties.TOKEN_PREFIX) || !JwtUtil.validateToken(token)) {
            chain.doFilter(request, response);
            return;
        }

        // 토큰이 있지만 만료된 경우
        if(!JwtUtil.validateToken(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            chain.doFilter(request, response);
            return;
        }

        // 유효한 토큰인 경우 - 아이디를 통해 사용자 존재 여부/권한 확인
        String id = JwtUtil.getId(token);
        if(id != null) {
            UserDto userDto = userRepository.selectOneById(id);

            // 권한 관리를 위해 SecurityContext에 인증 정보 저장
            PrincipalDetails principalDetails = new PrincipalDetails(userDto);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails.getUserDto().getId(), null, principalDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 새로운 토큰 발급
            String newToken = JwtUtil.createToken(principalDetails);

            // 응답 헤더에 토큰, 유저 정보, 인증 정보 추가
            response.setHeader(JwtProperties.HEADER_STRING, newToken);
            response.setHeader("user", AuthUtil.userToJson(userDto));
        }

        chain.doFilter(request, response);
    }
}