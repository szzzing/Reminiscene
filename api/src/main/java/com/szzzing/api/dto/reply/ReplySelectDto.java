package com.szzzing.api.dto.reply;

import lombok.Data;

@Data
public class ReplySelectDto {
    private String refId;
    private Integer page;
    private final int limit = 4;
    private int offset;

    public void setOffset() {
        offset = limit * (page-1);
    }
}