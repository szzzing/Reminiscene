package com.szzzing.api.security.filter;

import com.szzzing.api.dto.user.TokenRedisDto;
import com.szzzing.api.dto.user.UserDto;
import com.szzzing.api.repository.TokenRepository;
import com.szzzing.api.repository.UserRepository;
import com.szzzing.api.security.auth.AuthUtil;
import com.szzzing.api.security.auth.PrincipalDetails;
import com.szzzing.api.security.jwt.JwtProperties;
import com.szzzing.api.security.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class UserAuthorizationFilter extends BasicAuthenticationFilter {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public UserAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, TokenRepository tokenRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 헤더에 토큰을 담아 보냈는지(인증받은 사용자인지) 검사하는 과정
        String accessToken = JwtUtil.getToken(request);

        // 헤더에 토큰이 없는 경우 (미인증 사용자)
        if(accessToken == null) {
            chain.doFilter(request, response);
            return;
        }

        try {
            // 액세스 토큰 만료
            if(!JwtUtil.validateToken(accessToken)) {
                String refreshToken = tokenRepository.findRefreshToken(accessToken);
                // 리프레시 토큰도 만료
                if(!JwtUtil.validateToken(refreshToken)) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    return;
                // 리프레시 토큰은 유효 - 새로운 액세스 토큰 발급
                } else {
                    accessToken = JwtUtil.createAccessToken(JwtUtil.getId(refreshToken));
                    tokenRepository.saveRefreshToken(accessToken, refreshToken);
                }
            }
            // 액세스 토큰에 저장된 아이디를 통해 사용자 존재 여부/권한 확인
            UserDto userDto = userRepository.selectOneUser(JwtUtil.getId(accessToken));

            // 권한 관리를 위해 SecurityContext에 인증 정보 저장
            PrincipalDetails principalDetails = new PrincipalDetails(userDto);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails.getUserDto().getId(), null, principalDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
    
            // 응답 헤더에 토큰, 유저 정보 추가
            response.setHeader(JwtProperties.HEADER_STRING, accessToken);
            response.setHeader("user", AuthUtil.userToJson(userDto));

        } catch(Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        chain.doFilter(request, response);
    }
}