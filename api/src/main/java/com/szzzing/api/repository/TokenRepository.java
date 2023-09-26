package com.szzzing.api.repository;

import com.szzzing.api.dto.user.TokenRedisDto;
import com.szzzing.api.security.jwt.JwtProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * The type Token repository.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
@ComponentScan("com.szzzing.api")
public class TokenRepository {
    private static final String TYPE = "TOKEN : ";
    @Qualifier("tokenRedisTemplate")
    private final RedisTemplate<String, String> redisTemplate;

    private static String getKey(String accessToken) {
        return TYPE + accessToken;
    }

    /**
     * 리프레시 토큰 저장
     *
     * @param accessToken  key
     * @param refreshToken value
     */
    public void saveRefreshToken(String accessToken, String refreshToken) {
        String key = getKey(accessToken);

        // 새로운 인증번호 추가, Strings는 기존 key값 대체
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, refreshToken);
        redisTemplate.expire(key, JwtProperties.REFRESH_EXPIRATION_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 액세스 토큰을 통해 리프레시 토큰 검색
     *
     * @param accessToken key
     * @return the string
     */
    public String findRefreshToken(String accessToken) {
        String key = getKey(accessToken);

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    /**
     * 리프레시 토큰 삭제
     *
     * @param accessToken  key
     * @return the boolean
     */
    public boolean deleteRefreshToken(String accessToken) {
        String key = getKey(accessToken);
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }
}
