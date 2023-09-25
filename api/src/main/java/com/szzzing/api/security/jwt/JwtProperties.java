package com.szzzing.api.security.jwt;

// Jwt 토큰 관련 정보
public class JwtProperties {
    public static final String SECRET = "reminiscene"; // 서버만 알고 있는 개인키
    public static final int ACCESS_EXPIRATION_TIME = 1000*60*10; // 10분
    public static final int REFRESH_EXPIRATION_TIME = 1000*60*30; // 30분
    public static final String TOKEN_PREFIX = "Bearer ";   // Jwt 토큰 prefix로 반드시 붙여줘야 하는 문자열
    public static final String HEADER_STRING = "access_token";    // 헤더에 담을 토큰의 key값
}
