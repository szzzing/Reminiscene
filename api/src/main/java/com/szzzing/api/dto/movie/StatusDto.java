package com.szzzing.api.dto.movie;

import lombok.Data;

@Data
public class StatusDto {
    private Integer rate;
    private boolean isComment;
    private boolean isBookmark;
    private boolean isWatching;
}