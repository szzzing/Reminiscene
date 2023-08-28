package com.szzzing.api.dto.movie;

import lombok.Data;

@Data
public class StasticsDto {
    private String id;
    private float avgRate;
    private int rateCount;
    private int wishCount;
    private int watchingCount;
}
