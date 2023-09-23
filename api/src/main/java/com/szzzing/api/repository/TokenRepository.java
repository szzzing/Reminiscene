package com.szzzing.api.repository;

import com.szzzing.api.dto.mail.CodeDto;
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

@Repository
@RequiredArgsConstructor
@Slf4j
@ComponentScan("com.szzzing.api")
public class TokenRepository {
    private final RedisTemplate redisTemplate;

    public void save(TokenRedisDto tokenRedisDto) {
        boolean result = false;

        String key = tokenRedisDto.getId() +"_"+ tokenRedisDto.getType();

        // 새로운 인증번호 추가, Strings는 기존 key값 대체
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, tokenRedisDto.getToken());
        redisTemplate.expire(key, JwtProperties.EXPIRATION_TIME, TimeUnit.MILLISECONDS);

        log.info(key);
        log.info(valueOperations.get(key));
    }

    public void match(CodeDto codeDto) {
    }

    public boolean delete(TokenRedisDto tokenRedisDto) {
        String key = tokenRedisDto.getId()+"_"+tokenRedisDto.getType();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        log.info(key);
        log.info(valueOperations.get(key));
        return redisTemplate.delete(key);
    }
}
