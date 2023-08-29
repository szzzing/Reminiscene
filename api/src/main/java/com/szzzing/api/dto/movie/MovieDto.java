package com.szzzing.api.dto.movie;

import lombok.Data;

import java.sql.Date;

@Data
public class MovieDto {
    private int id;
    private String title;
    private String originalTitle;
    private String tagline;
    private String overview;
    private Date releaseDate;
    private String backdropPath;
    private String posterPath;
    private String genre;
    private int runtime;
    private float avgRate;
}
