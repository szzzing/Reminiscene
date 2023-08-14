package com.szzzing.api.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.szzzing.api.security.auth.PrincipalDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtUtil {

    // 토큰 생성
    public static String createToken(PrincipalDetails principalDetails) {
        String token = JWT.create()
                .withSubject(principalDetails.getUsername())    // jwt 이름
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))  // 만료시간
                .withClaim("id", principalDetails.getUser().getId())   // payload 부분에서 private 설정. private의 이름과 값 지정
                .withClaim("email", principalDetails.getUser().getEmail())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET)); // 시크릿키, 해싱 알고리즘

        token = JwtProperties.TOKEN_PREFIX + token;

        return token;
    }

    // request에서 토큰 가져오기
    public static String getToken(HttpServletRequest request) {
        return request.getHeader(JwtProperties.HEADER_STRING);
    }

    // 토큰 유효성 검증
    public static boolean validateToken(String header) {
        try {
            String token = header.replace(JwtProperties.TOKEN_PREFIX, "");
            JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token);
        } catch(TokenExpiredException e) {
            log.info(e.getMessage());
            return false;
        }
        return true;
    }

    // 토큰에서 아이디 정보 획득
    public static String getId(String header) {
        String token = header.replace(JwtProperties.TOKEN_PREFIX, "");
        return JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token).getClaim("id").asString();
    }
}
