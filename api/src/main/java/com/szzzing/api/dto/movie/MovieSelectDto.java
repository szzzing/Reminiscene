package com.szzzing.api.dto.movie;

import lombok.Data;

@Data
public class MovieSelectDto {
    private Integer page;
    private final int limit = 5;
    private int offset;
    private String sort;
    private String loginUser;    // 로그인시 코멘트 좋아요 여부 체크
    private String query;

    public void setOffset() {
        offset = limit * (page-1);
    }
}