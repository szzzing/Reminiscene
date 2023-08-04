package com.szzzing.app.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
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
        System.out.println("JwtAuthorizationFilter");
        // 헤더에 토큰을 담아 보냈는지(인증받은 사용자인지) 검사하는 과정
        String header = request.getHeader(JwtProperties.HEADER_STRING);
        // 헤더에 토큰이 없는 경우, JwtAuthorizationFilter을 통과하고 JwtAuthenticationFilter에서 로그인을 진행하도록 리턴
        if(header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            System.out.println("인증없음");
            chain.doFilter(request, response);
            return;
        }
        // 헤더에 토큰이 있는 경우, 정보 확인
        System.out.println("인증있음"+request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, ""));
        String token = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");
        String id = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token).getClaim("id").asString();
        // 아이디를 통해 사용자 존재 여부, 권한 확인
        if(id != null) {
            System.out.println("아이디있음");
            User user = userRepository.selectOneById(id);
            PrincipalDetails principalDetails = new PrincipalDetails(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails.getUser().getId(), null, principalDetails.getAuthorities());
            // 권한 관리를 위해 SecurityContext에 인증 정보 저장rincipalDetails.getAuthorities()
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        chain.doFilter(request, response);
    }
}