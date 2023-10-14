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
    private static final String KEY = "EMAIL : ";
    private static final long EXPIRE = 180L;
    @Qualifier("mailRedisTemplate")
    private final RedisTemplate<String, Integer> redisTemplate;

    private static String getKey(String email) {
        return KEY + email;
    }

    /**
     * Save code boolean.
     *
     * @param mailRedisDto the mail redis dto
     * @return the boolean
     */
    public boolean saveCode(MailRedisDto mailRedisDto) {
        String key = getKey(mailRedisDto.getEmail());
        
        // 새로운 인증번호 추가, Strings는 기존 key값 대체
        ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, Integer.valueOf(String.valueOf(mailRedisDto.getCode())));
        redisTemplate.expire(key, EXPIRE, TimeUnit.SECONDS);

        log.info("인증번호 : " + Objects.requireNonNull(valueOperations.get(key)));
        return valueOperations.get(key) != null;
    }

    /**
     * 입력한 코드와 redis에 저장된 코드 비교
     *
     * @param codeDto the code dto
     * @return the boolean
     */
    public boolean matchCode(CodeDto codeDto) {
        String key = getKey(codeDto.getEmail());

        ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();
        Integer code = valueOperations.get(key);
        if(code==null) {
            return false;
        // 인증 성공시 redis에 3분 유지
        } else if(code==codeDto.getCode()) {
            MailRedisDto mailRedisDto = new MailRedisDto();
            mailRedisDto.setEmail(codeDto.getEmail());
            mailRedisDto.setCode(code);
            saveCode(mailRedisDto);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 해당 이메일로 인증 중인 유저가 있는지 탐색
     *
     * @param email 이메일
     * @return 결과
     */
    public boolean getMailStatus(String email) {
        String key = getKey(email);
        ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key)==null;
    }
}
