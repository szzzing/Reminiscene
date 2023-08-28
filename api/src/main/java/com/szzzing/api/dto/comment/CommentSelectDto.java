package com.szzzing.api.dto.comment;

import lombok.Data;

@Data
public class CommentSelectDto {
    private int id;
    private String userId;
    private String movieId;
    private Integer page;
    private final int limit = 4;
    private int offset;
    private String sort;
    private String loginUser;    // 로그인시 코멘트 좋아요 여부 체크

    public void setOffset() {
        offset = limit * (page-1);
    }
}