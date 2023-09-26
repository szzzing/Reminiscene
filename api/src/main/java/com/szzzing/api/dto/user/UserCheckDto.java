package com.szzzing.api.dto.user;

import lombok.Data;

@Data
public class UserCheckDto {
    private String id;
    private String email;
    private String nickname;
    private String loginUser;
}
