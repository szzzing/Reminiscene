package com.szzzing.api.dto.comment;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class CommentDto {
    private Integer id;
    private String movieId;
    private String movieTitle;
    private Date movieReleaseDate;
    private String movieGenre;
    private String moviePosterPath;
    private String userId;
    private String nickname;
    private String profileImage;
    private boolean isWatching;
    private boolean isWish;
    private int rate;
    private String content;
    private boolean isSpoiler;
    private int likeCount;
    private int replyCount;
    private Timestamp creDate;
    private Timestamp modDate;
    private boolean userLike;
}