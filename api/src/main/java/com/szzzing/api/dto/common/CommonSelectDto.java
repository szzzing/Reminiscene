package com.szzzing.api.dto.common;

import lombok.Data;

@Data
public class CommonSelectDto {
    private int id;
    private String userId;
    private String movieId;
    private Integer page;
    private final int limit = 4;
    private int offset;
    private String loginUser;

    public void setOffset() {
        offset = limit * (page-1);
    }
}