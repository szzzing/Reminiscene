package com.szzzing.api.dto.movie;

import lombok.Data;

@Data
public class RateDto {
    private int movieId;
    private String userId;
    private int rate;
}
