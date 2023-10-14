package com.szzzing.api.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Redis 전송용 메일 Dto
 */
@Data
public class MailRedisDto {
    private String email;
    private int code;
}
