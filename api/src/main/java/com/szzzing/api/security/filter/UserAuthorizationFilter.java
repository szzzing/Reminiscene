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

/**
 * 요청 시 인증
 */
@Slf4j
public class UserAuthorizationFilter extends BasicAuthenticationFilter {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    /**
     * Instantiates a new User authorization filter.
     *
     * @param authenticationManager the authentication manager
     * @param userRepository        the user repository
     * @param tokenRepository       the token repository
     */
    public UserAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, TokenRepository tokenRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    // 헤더에 토큰을 담아 보냈는지 검증하는 과정
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String accessToken = JwtUtil.getToken(request);

        // 토큰 부재 - 리턴
        if(accessToken == null) {
            chain.doFilter(request, response);
        }

        // 토큰 존재
        try {
            // 액세스 토큰 만료
            if(!JwtUtil.validateToken(accessToken)) {
                String refreshToken = tokenRepository.findRefreshToken(accessToken);
                // 리프레시 토큰도 만료 - 리턴
                if(!JwtUtil.validateToken(refreshToken)) {
                    return;
                // 리프레시 토큰은 유효 - 새로운 액세스 토큰 발급
                } else {
                    tokenRepository.deleteRefreshToken(accessToken);
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
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return;
        }

        chain.doFilter(request, response);
    }
}