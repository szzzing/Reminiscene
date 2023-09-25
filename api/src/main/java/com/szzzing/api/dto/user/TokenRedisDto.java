package com.szzzing.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenRedisDto {
    private String key;
    private String token;
    private String type;    // A: 액세스, R: 리프레시
}
