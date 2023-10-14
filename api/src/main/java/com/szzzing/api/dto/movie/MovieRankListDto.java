package com.szzzing.api.dto.movie;

import lombok.Data;

import java.util.ArrayList;

@Data
public class MovieRankListDto {
    private ArrayList<MovieDto> monthBestList;
    private ArrayList<MovieDto> bestRateList;
    private ArrayList<MovieDto> newList;
}