package com.szzzing.api.dto.movie;

import lombok.Data;

@Data
public class StatusDto {
    private int rate;
    private boolean isComment;
    private boolean isWish;
    private boolean isWatching;
    private String commentContent;
    private boolean commentIsSpoiler;
}