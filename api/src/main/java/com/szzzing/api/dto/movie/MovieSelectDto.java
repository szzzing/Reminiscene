package com.szzzing.api.dto.movie;

import lombok.Data;

@Data
public class MovieSelectDto {
    private Integer page;
    private final int limit = 4;
    private int offset;
    private String sort;
    private String loginUser;    // 로그인시 코멘트 좋아요 여부 체크

    public void setOffset() {
        offset = limit * (page-1);
    }
}