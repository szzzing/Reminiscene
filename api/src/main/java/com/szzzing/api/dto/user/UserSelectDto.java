package com.szzzing.api.dto.user;

import lombok.Data;

@Data
public class UserSelectDto {
    private String query;
    private String id;
    private int page;
    private final int limit = 6;
    private int offset;
    private String loginUser;

    public void setOffset() {
        offset = limit * (page-1);
    }
}