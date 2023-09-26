package com.szzzing.api.repository;

import com.szzzing.api.dto.mail.CodeDto;
import com.szzzing.api.dto.mail.MailRedisDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * The type Mail repository.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class MailRepository {
    @Qualifier("mailRedisTemplate")
    private final RedisTemplate<String, Integer> redisTemplate;

    /**
     * Save code boolean.
     *
     * @param mailRedisDto the mail redis dto
     * @return the boolean
     */
    public boolean saveCode(MailRedisDto mailRedisDto) {
        String key = mailRedisDto.getEmail() +"_"+ mailRedisDto.getType();
        
        // 새로운 인증번호 추가, Strings는 기존 key값 대체
        ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, Integer.valueOf(String.valueOf(mailRedisDto.getCode())));
        redisTemplate.expire(key, 180L, TimeUnit.SECONDS);

        log.info("인증번호 : " + Objects.requireNonNull(valueOperations.get(key)).toString());
        return true;
    }

    /**
     * 입력한 코드와 redis에 저장된 코드 비교
     *
     * @param codeDto
     * @return the boolean
     */
    public boolean matchCode(CodeDto codeDto) {
        String key = codeDto.getEmail()+"_"+codeDto.getType();
        ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();
        Integer code = valueOperations.get(key);
        if(code==null) return false;

        return code==codeDto.getCode();
    }
}
