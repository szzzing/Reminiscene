package com.szzzing.api.dto.movie;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommentDto {
    private Integer id;
    private String movieId;
    private String userId;
    private String nickname;
    private boolean isWatching;
    private boolean isWish;
    private int rate;
    private String content;
    private boolean isSpoiler;
    private int likeCount;
    private int replyCount;
    private Timestamp creDate;
    private Timestamp modDate;
}