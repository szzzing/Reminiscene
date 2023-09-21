package com.szzzing.api.repository;

import com.szzzing.api.dto.mail.CodeDto;
import com.szzzing.api.dto.mail.MailRedisDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MailRepository {
    private final RedisTemplate redisTemplate;

    public void save(MailRedisDto mailRedisDto) {
        String key = mailRedisDto.getEmail() +"_"+ mailRedisDto.getType();
        
        // 기존 인증번호 삭제
        redisTemplate.delete(key);
        
        // 새로운 인증번호 추가
        ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, Integer.valueOf(String.valueOf(mailRedisDto.getCode())));
        redisTemplate.expire(key, 60L, TimeUnit.SECONDS);

        // log
        Integer code = valueOperations.get(key);
        log.info(key);
        log.info(code.toString());
    }

    public boolean match(CodeDto codeDto) {
        String key = codeDto.getEmail()+"_"+codeDto.getType();
        ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();
        Integer code = valueOperations.get(key);
        if(code==null) return false;

        return code==codeDto.getCode();
    }
}
