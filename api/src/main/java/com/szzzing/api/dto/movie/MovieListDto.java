package com.szzzing.api.dto.movie;

import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;

@Data
public class MovieListDto {
    private ArrayList<MovieDto> list;
    private int page;
}
