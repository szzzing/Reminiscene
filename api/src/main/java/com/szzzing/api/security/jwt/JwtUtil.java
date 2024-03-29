package com.szzzing.api.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.szzzing.api.security.auth.PrincipalDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

/**
 * The type Jwt util.
 */
@Slf4j
@RequiredArgsConstructor
public class JwtUtil {
    /**
     * 액세스 토큰 생성
     *
     * @param id 아이디
     * @return 액세스 토큰
     */
    public static String createAccessToken(String id) {
        String token = JWT.create()
                .withSubject("ACCESS")    // jwt 이름
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.ACCESS_EXPIRATION_TIME))  // 만료시간
                .withClaim("id", id)   // payload 부분에서 private 설정. private의 이름과 값 지정
                .sign(Algorithm.HMAC512(JwtProperties.SECRET)); // 시크릿키, 해싱 알고리즘

        return JwtProperties.TOKEN_PREFIX + token;
    }

    /**
     * 리프레시 토큰 생성
     *
     * @param id 아이디
     * @return 리프레시 토큰
     */
    public static String createRefreshToken(String id) {
        String token = JWT.create()
                .withSubject("REFRESH")    // jwt 이름
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.REFRESH_EXPIRATION_TIME))  // 만료시간
                .withClaim("id", id)   // payload 부분에서 private 설정. private의 이름과 값 지정
                .sign(Algorithm.HMAC512(JwtProperties.SECRET)); // 시크릿키, 해싱 알고리즘

        return JwtProperties.TOKEN_PREFIX + token;
    }

    /**
     * 헤더에서 액세스 토큰 가져오기
     *
     * @param request the request
     * @return 액세스 토큰
     */
    public static String getToken(HttpServletRequest request) {
        return request.getHeader(JwtProperties.HEADER_STRING);
    }

    /**
     * 토큰 유효성 검증
     *
     * @param token 검증할 토큰
     * @return 검증결과
     */
    public static boolean validateToken(String token) {
        if(token==null) return false;
        try {
            token = token.replace(JwtProperties.TOKEN_PREFIX, "");
            JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token);
        } catch(TokenExpiredException e) {
            log.info(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 토큰에서 아이디 정보 획득
     *
     * @param token 토큰
     * @return the id
     */
    public static String getId(String token) {
        token = token.replace(JwtProperties.TOKEN_PREFIX, "");
        return JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token).getClaim("id").asString();
    }
}
