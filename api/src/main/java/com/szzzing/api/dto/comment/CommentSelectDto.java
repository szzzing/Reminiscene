package com.szzzing.api.dto.comment;

import lombok.Data;

@Data
public class CommentSelectDto {
    private String movieId;
    private final int limit = 2;
    private int offset;
    private String sort;

    public void setOffset(int page) {
        offset = limit * (page-1);
    }
}