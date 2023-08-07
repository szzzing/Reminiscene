package com.szzzing.app.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtil {

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
