package com.szzzing.api.security.jwt;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Jwt 토큰에 관한 정보
 */
@Component
@Slf4j
public class JwtProperties {
    @Value("${spring.jwt.secret}")
    private String secret;

    public JwtProperties() {
        log.info(String.format("%s, %s", secret, SECRET));
    }

    /**
     * 시크릿키 삽입
     */
    @PostConstruct
    public void init() {
        SECRET = this.secret;
        log.info(String.format("%s, %s", secret, SECRET));
    }

    /**
     * 토큰 시크릿키
     */
    public static String SECRET;
    /**
     * 액세스토큰 만료시간
     */
    public final static int ACCESS_EXPIRATION_TIME = 1000*60*10;
    /**
     * 리프레시토큰 만료시간
     */
    public final static int REFRESH_EXPIRATION_TIME = 1000*60*30;
    /**
     * 토큰 접두어
     */
    public final static String TOKEN_PREFIX = "Bearer ";
    /**
     * 헤더에 저장된 토큰 키
     */
    public final static String HEADER_STRING = "access_token";
}
