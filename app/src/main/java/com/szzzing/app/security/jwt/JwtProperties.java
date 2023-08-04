package com.szzzing.app.security.jwt;

// Jwt 토큰 관련 정보
public class JwtProperties {
    static final String SECRET = "reminiscene"; // 서버만 알고 있는 개인키
    static final int EXPIRATION_TIME = 1000*60*30; // 30분
    static final String TOKEN_PREFIX = "Bearer ";   // Jwt 토큰 prefix로 반드시 붙여줘야 하는 문자열
    static final String HEADER_STRING = "Authorization";    // 헤더에 담을 토큰의 key값
}
