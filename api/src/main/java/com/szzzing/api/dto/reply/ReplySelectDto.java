package com.szzzing.api.dto.reply;

import lombok.Data;

@Data
public class ReplySelectDto {
    private String refId;
    private final int limit = 8;
    private int offset;

    public void setOffset(int page) {
        offset = limit * (page-1);
    }
}