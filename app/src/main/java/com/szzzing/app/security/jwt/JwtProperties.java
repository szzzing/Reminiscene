package com.szzzing.app.security.jwt;

public class JwtProperties {
    static final String SECRET = "reminiscene"; // 서버만 알고 있는 개인키
    static final int EXPIRATION_TIME = 1000*60*30; // 10초 (1/1000초)
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
}
