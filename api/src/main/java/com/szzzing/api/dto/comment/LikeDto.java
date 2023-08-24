package com.szzzing.api.dto.comment;

import lombok.Data;

@Data
public class LikeDto {
    private String userId;
    private int commentId;
}
