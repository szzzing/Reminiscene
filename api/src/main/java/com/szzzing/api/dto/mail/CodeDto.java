package com.szzzing.api.dto.mail;

import lombok.Data;

@Data
public class CodeDto {
    private String email;
    private int code;
    private int type;  // 이메일인증: E, 비밀번호찾기: P
}
