package com.szzzing.api.dto.movie;

import lombok.Data;

@Data
public class MovieDto {
    private String id;
    private int avgRate;
    private int rateCount;
    private int commentCount;
    private int watchingCount;
    private int bookmarkCount;
}
