package com.szzzing.api.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The type Mail redis dto.
 * type
 * E: 이메일 인증
 * P: 비밀번호 찾기 인증
 */
@Data
public class MailRedisDto {
    private String email;
    private int code;
}
