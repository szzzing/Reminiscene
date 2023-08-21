package com.szzzing.api.dto.movie;

import lombok.Data;

@Data
public class RateDto {
    private String movieId;
    private String userId;
    private int star;
}
